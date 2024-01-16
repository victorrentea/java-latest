package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import victor.training.java.virtualthread.bar.Beer;
import victor.training.java.virtualthread.bar.DillyDilly;
import victor.training.java.virtualthread.bar.UserPreferences;
import victor.training.java.virtualthread.bar.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate restTemplate;
  private final WebClient webClient;

  @GetMapping("/drink")
  public DillyDilly drinkFuture() throws Exception {
    var pref = fetchPreferences();
    var beerFuture = supplyAsync(()->fetchBeer(pref), Executors.newVirtualThreadPerTaskExecutor());
    var vodkaFuture = supplyAsync(this::fetchVodka, Executors.newVirtualThreadPerTaskExecutor());
    var dilly = new DillyDilly(beerFuture.get(), vodkaFuture.get());
    log.info("Returning: {}", dilly);
    return dilly;
  }
  // ✅ HTTP Threads are released immediately: save memory and avoid starvation
  // ❌ Hard to read

  //region Structured Concurrency (NOT yet production-ready in Java 21 LTS)
  @GetMapping("/drink-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    UserPreferences pref = restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);

    try (var scope = new ShutdownOnFailure()) {
      var beerTask = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class)); // +1 child virtual thread
      var vodkaTask = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class)); // +1 child virtual thread

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
    // return restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    return webClient.get().uri("http://localhost:9999/api/user-preferences").retrieve()
        .bodyToMono(UserPreferences.class)
        .block();
  }

  private Beer fetchBeer(UserPreferences pref) {
    // return  restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class);
    return webClient.get().uri("http://localhost:9999/api/beer/" + pref.favoriteBeerType()).retrieve()
        .bodyToMono(Beer.class)
        .block();
  }

  private Vodka fetchVodka() {
    // return restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class);
    return webClient.get().uri("http://localhost:9999/vodka").retrieve()
        .bodyToMono(Vodka.class)
        .block();
  }
}

