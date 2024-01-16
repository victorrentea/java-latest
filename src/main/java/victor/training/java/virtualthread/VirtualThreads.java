package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.java.virtualthread.bar.Beer;
import victor.training.java.virtualthread.bar.DillyDilly;
import victor.training.java.virtualthread.bar.UserPreferences;
import victor.training.java.virtualthread.bar.Vodka;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate restTemplate;

  @GetMapping("/drink")
  public CompletableFuture<DillyDilly> drinkFuture() throws Exception {
    var preferencesFuture = supplyAsync(() -> fetchPreferences());

    var beerFuture = preferencesFuture.thenApply(pref -> fetchBeer(pref));

    var vodkaFuture = supplyAsync(() -> fetchVodka());

    var dillyFuture = beerFuture.thenCombine(vodkaFuture, DillyDilly::new);

    dillyFuture.thenAccept(dilly -> log.info("Returning: {}", dilly));

    return dillyFuture;
  }
  // ✅ HTTP Threads are released immediately: save memory and avoid starvation
  // ❌ Hard to read

  //region Structured Concurrency (NOT yet production-ready in Java 21 LTS)
  @GetMapping("/drink-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    UserPreferences pref = fetchPreferences();

    try (var scope = new ShutdownOnFailure()) {
      var beerTask = scope.fork(() -> fetchBeer(pref)); // +1 child virtual thread
      var vodkaTask = scope.fork(() -> fetchVodka()); // +1 child virtual thread

      scope.join().throwIfFailed(); // throw exception if any subtasks failed

      return new DillyDilly(beerTask.get(), vodkaTask.get());
    }
  }
  // Virtual Threads + Structured Concurrency
  // ✅ Memory-efficient, as virtual threads are very cheap
  // ✅ easy to read
  // ✅ if client cancels => subtasks are cancelled
  // ✅ if one subtask fails => the other task is interrupted
  // ✅ JFR profiler can link children threads with parent thread
  // ❌ 'synchronized' blocks a 'full thread' (not a virtual thread)
  // ❌ no better for CPU Intensive flows
  //endregion

  private UserPreferences fetchPreferences() {
    return restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    // the true non-blocking way that wastes NO threads:
//    return webClient.builder().build().get().uri("http://localhost:9999/api/user-preferences").retrieve()
//      .bodyToMono(UserPreferences.class).toFuture();
  }


  private Beer fetchBeer(UserPreferences pref) {
    return restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class);
  }

  private Vodka fetchVodka() {
    return restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class);
  }
}

