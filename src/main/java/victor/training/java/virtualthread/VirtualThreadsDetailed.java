package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.java.virtualthread.bar.Beer;
import victor.training.java.virtualthread.bar.DillyDilly;
import victor.training.java.virtualthread.bar.UserPreferences;
import victor.training.java.virtualthread.bar.Vodka;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreadsDetailed {
  private final RestTemplate restTemplate;
  private static final AtomicInteger indexCounter = new AtomicInteger(1);

  @GetMapping("/beer")
  public Beer seq() {
    MDC.put("reqId", "#%04d".formatted(indexCounter.getAndIncrement())); // %X{reqId} in application.properties
    UserPreferences preferences = restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    log.info("Got preferences: {}", preferences);
    Beer beer = fetchBeer(preferences.favoriteBeerType());
    log.info("END Got beer: {}", beer);
    return beer;
  }


  @GetMapping("/drink-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    MDC.put("reqId", "#%04d".formatted(indexCounter.incrementAndGet())); // %X{reqId} in application.properties
    long t0 = currentTimeMillis();

    // Structured Concurrency (not yet production-ready in Java 21 LTS)
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<Beer> beerTask = scope.fork(() -> fetchBeer("blond")); // spawns +1 child virtual thread
      Subtask<Vodka> vodkaTask = scope.fork(() -> fetchVodka()); // spawns +1 child virtual thread

      scope.joinUntil(Instant.now().plusSeconds(10)) // block the parent virtual thread until all child subtasks complete
          .throwIfFailed(); // throw exception if any subtasks failed

      DillyDilly dilly = new DillyDilly(beerTask.get(), vodkaTask.get()); // Combine subtasks
      log.info("Request completed in {} ms", currentTimeMillis() - t0);
      return dilly;
    }
  }

  @GetMapping("/drink-cf")
  public CompletableFuture<DillyDilly> drinkCF() throws InterruptedException, ExecutionException, TimeoutException {
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerCF = supplyAsync(() -> fetchBeer("blond"));

    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka());

    CompletableFuture<DillyDilly> dillyCF = beerCF.thenCombine(vodkaCF, DillyDilly::new);
    log.info("HTTP Thread released in {} ms", currentTimeMillis() - t0);
    // ✅ HTTP Thread released immediately, but:
    // ❌ client cancellation does not propagate upstream
    // ❌ if one subtask fails, the other is NOT interrupted
    // ❌ JFR profiler does not show the subtasks
    return dillyCF;
  }

  // Scope equivalent with Completable Future (until Java 25 LTS)
  @GetMapping("/drink-cfv")
  public DillyDilly drinkCFV() throws InterruptedException, ExecutionException, TimeoutException {
    long t0 = currentTimeMillis();

    CompletableFuture<Beer> beerCF = supplyAsync(() -> fetchBeer("blond"));

    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka());

    DillyDilly dilly = beerCF.thenCombine(vodkaCF, DillyDilly::new)
        .get(10, SECONDS); // not a problem, since I'm blocking a virtual thread
    // ❌ cancellation does not propagate upstream
    // ❌ if one subtask fails, the other is NOT interrupted
    // ❌ JFR does not show the subtasks
    log.info("HTTP Thread released in {} ms", currentTimeMillis() - t0);
    return dilly;
  }


  private Vodka fetchVodka() {
    return restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class);
  }

  private Beer fetchBeer(String type) {
    return restTemplate.getForObject("http://localhost:9999/api/beer/" + type, Beer.class);
  }

}

