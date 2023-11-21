package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

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
    Beer beer = fetchBeer(preferences.favoriteBeerType());
    log.info("END Got beer: {}", beer);
    return beer;
  }

  @GetMapping("/drink")
  public DillyDilly drink() throws InterruptedException, ExecutionException {
    MDC.put("reqId", "#%04d".formatted(indexCounter.incrementAndGet())); // %X{reqId} in application.properties
    long t0 = currentTimeMillis();

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<Beer> beerTask = scope.fork(() -> fetchBeer("blond")); // spawns +1 child virtual thread
      Subtask<Vodka> vodkaTask = scope.fork(() -> fetchVodka()); // spawns +1 child virtual thread

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
  public DillyDilly drinkCF() throws InterruptedException, ExecutionException, TimeoutException {
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerCF = supplyAsync(() -> fetchBeer("blond"), newVirtualThreadPerTaskExecutor());

    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka(), newVirtualThreadPerTaskExecutor());

    DillyDilly dilly = beerCF.thenCombine(vodkaCF, DillyDilly::new)
        .get(1, TimeUnit.MINUTES); // no problem, since I'm blocking a virtual thread
    // - cancellation does not propagate upstream
    // - a failed subtask does NOT fail the other
    log.info("Request completed in {} ms", currentTimeMillis() - t0);
    return dilly;
  }


  record UserPreferences(String favoriteBeerType, boolean iceInVodka) {
  }

  record Beer(String type) {
  }

  record Vodka(String type) {
  }

  record DillyDilly(Beer beer, Vodka vodka) {
  }


  private Vodka fetchVodka() {
    return rest.getForObject("http://localhost:9999/vodka", Vodka.class);
  }

  private Beer fetchBeer(String blond) {
    return rest.getForObject("http://localhost:9999/api/beer/" + blond, Beer.class);
  }

}
