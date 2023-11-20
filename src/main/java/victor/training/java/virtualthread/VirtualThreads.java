package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate rest;
  private static final AtomicInteger indexCounter = new AtomicInteger(1);

  @GetMapping("/beer")
  public Beer seq() {
    MDC.put("reqId", "#%04d".formatted(indexCounter.getAndIncrement())); // %X{reqId} in application.properties
    UserPreferences preferences = rest.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    log.info("Got preferences: {}", preferences);
    Beer beer = rest.getForObject("http://localhost:9999/api/beer/" + preferences.favoriteBeerType(), Beer.class);
    log.info("END Got beer: {}", beer);
    return beer;
  }

  record UserPreferences(String favoriteBeerType, boolean iceInVodka) {
  }

  record Beer(String type) {
  }

  record Vodka(String type) {
  }

  record DillyDilly(Beer beer, Vodka vodka) {
  }

  @GetMapping("/drink")
  public DillyDilly drink() throws InterruptedException, ExecutionException {
    MDC.put("reqId", "#%04d".formatted(indexCounter.incrementAndGet())); // %X{reqId} in application.properties
    long t0 = currentTimeMillis();

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<Beer> beerTask = scope.fork(() ->
          rest.getForObject("http://localhost:9999/api/beer/blond", Beer.class)); // spawns +1 child virtual thread

      Subtask<Vodka> vodkaTask = scope.fork(() ->
          rest.getForObject("http://localhost:9999/vodka", Vodka.class)); // spawns +1 child virtual thread

      scope.join() // Blocks the parent virtual thread until all child subtasks complete
          .throwIfFailed(); // throw exception if any subtasks completed in error

      // Combine subtasks' results
      DillyDilly dilly = new DillyDilly(beerTask.get(), vodkaTask.get());
      log.info("Request completed in {} ms", currentTimeMillis() - t0);
      return dilly;
    }
  }


  // Scope equivalent with Completable Future (until Java 25 LTS)
  @GetMapping("/drink-cf")
  public DillyDilly drinkCF() throws InterruptedException, ExecutionException {
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerCF = CompletableFuture.supplyAsync(() ->
            rest.getForObject("http://localhost:9999/api/beer/blond", Beer.class),
        Executors.newVirtualThreadPerTaskExecutor());

    CompletableFuture<Vodka> vodkaCF = CompletableFuture.supplyAsync(() ->
            rest.getForObject("http://localhost:9999/api/vodka", Vodka.class),
        Executors.newVirtualThreadPerTaskExecutor());

    DillyDilly dilly = beerCF.thenCombine(vodkaCF, DillyDilly::new)
        .get(); // no problem, since I'm blocking a virtual thread
    log.info("Request completed in {} ms", currentTimeMillis() - t0);
    return dilly;
  }

}
