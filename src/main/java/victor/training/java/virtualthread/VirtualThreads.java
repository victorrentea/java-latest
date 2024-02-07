package victor.training.java.virtualthread;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate restTemplate;
  private final WebClient webClient;

  @GetMapping("/async")
  public void autumn(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
    AsyncContext asyncContext = request.startAsync();
    externalApiCall().thenAccept(userPreferences -> {


      try {
        asyncContext.getResponse().getWriter().write("Async work done: " +userPreferences);
      } catch (Exception e) {
        log.error("Async failed", e);
      }
      asyncContext.complete();
    });
    log.info("JETTY thread left here prints after 0ms");
  }

  private CompletableFuture<UserPreferences> externalApiCall() throws InterruptedException {
    // talking to anythign but a API requires a reactive driver
    // there are reactive drivers for JDBC(Mysql), JPA, MongoDB, Cassandra, Redis, Kafka, RabbitMQ, etc
//    Executors.newVirtualThreadPerTaskExecutor()
    CompletableFuture<HttpResponse<Object>> httpResponseCompletableFuture = HttpClient.newHttpClient()
        .sendAsync(null, null);
    // non blocking HTTP call to other API. Spring:
    return WebClient.create().get()
        .uri("http://localhost:9999/api/user-preferences")
        .retrieve()
        .bodyToMono(UserPreferences.class)
        .toFuture();
  }

  @GetMapping("/drink")
  public CompletableFuture<DillyDilly> drinkFuture() throws Exception {
    var preferencesFuture = fetchPreferences();
    var beerFuture = preferencesFuture.thenCompose(pref -> fetchBeer(pref));
    var vodkaFuture = fetchVodka();
    var dillyFuture = beerFuture.thenCombine(vodkaFuture, DillyDilly::new);
    dillyFuture.thenAccept(dilly -> log.info("Returning: {}", dilly));
    return dillyFuture;
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

  private CompletableFuture<UserPreferences> fetchPreferences() {
    // return restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    return webClient.get().uri("http://localhost:9999/api/user-preferences").retrieve()
        .bodyToMono(UserPreferences.class)
        .toFuture();
  }

  private CompletableFuture<Beer> fetchBeer(UserPreferences pref) {
    // return  restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class);
    return webClient.get().uri("http://localhost:9999/api/beer/" + pref.favoriteBeerType()).retrieve()
        .bodyToMono(Beer.class)
        .toFuture();
  }

  private CompletableFuture<Vodka> fetchVodka() {
    // return restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class);
    return webClient.get().uri("http://localhost:9999/vodka").retrieve()
        .bodyToMono(Vodka.class)
        .toFuture();
  }
}

