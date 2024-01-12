package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.java.virtualthread.bar.Beer;
import victor.training.java.virtualthread.bar.DillyDilly;
import victor.training.java.virtualthread.bar.UserPreferences;
import victor.training.java.virtualthread.bar.Vodka;

import java.time.Instant;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate restTemplate;

  @GetMapping("/drink")
  public CompletableFuture<DillyDilly> drinkCF() throws Exception {
    long t0 = currentTimeMillis();

    // Mono<>, .flatMap, .zip
    CompletableFuture<UserPreferences> preferencesCF = supplyAsync(() ->
        restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class));

    CompletableFuture<Beer> beerCF = preferencesCF.thenApply(pref ->
        restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class));

    CompletableFuture<Vodka> vodkaCF = supplyAsync(() ->
        restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class));

    // Mono<> = .zip
    CompletableFuture<DillyDilly> dillyCF = beerCF.thenCombine(vodkaCF, DillyDilly::new)
        .orTimeout(10, SECONDS);

    dillyCF.thenAccept(dilly -> log.info("Returning: {}", dilly));
    log.info("HTTP Thread released in {} ms", currentTimeMillis() - t0);
    // ✅ Memory-efficient, because HTTP Thread released immediately, but:
    // ❌ client cancellation does not propagate upstream
    // ❌ if one subtask fails, the other is NOT interrupted
    // ❌ JFR profiler does not show the subtasks
    return dillyCF;
  }


  //region Structured Concurrency (NOT yet production-ready in Java 21 LTS)
  @GetMapping("/drink-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    UserPreferences pref = restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);

    try (StructuredTaskScope.ShutdownOnFailure scope = new StructuredTaskScope.ShutdownOnFailure()) {
      var beerTask = scope.fork(() -> restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class)); // +1 child virtual thread
      var vodkaTask = scope.fork(() -> restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class)); // +1 child virtual thread

      scope.joinUntil(Instant.now().plusSeconds(10)) // block the parent virtual thread until all child subtasks complete
          .throwIfFailed(); // throw exception if any subtasks failed

      return new DillyDilly(beerTask.get(), vodkaTask.get());
    }
  }
  //endregion

}

