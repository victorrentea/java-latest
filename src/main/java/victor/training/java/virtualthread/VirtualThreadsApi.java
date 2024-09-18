package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

//@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreadsApi {
  private final RestTemplate restTemplate;
  private final Executor virtualExecutor;

  @GetMapping("/warmup")
  public Beer warmup() throws InterruptedException {
    Thread.sleep(500);
    return new Beer("warm"); // initial performance comparison between PT and VT
  }

  @GetMapping("/sequential")
  public Beer sequential() {
    log.info("Start"); // TODO vrentea 12.09.2024: have this printed
    UserPreferences preferences = fetchPreferences();
    log.info("Preferences: {}", preferences);
    Beer beer = fetchBeer(preferences.favoriteBeerType());
    log.info("Got beer: {}", beer);
    return beer;
  }

  @GetMapping("/parallel-non-blocking")
  public CompletableFuture<DillyDilly> drinkCF() {
    long t0 = currentTimeMillis();
    // ⚠️ For a proper performance comparison, reimplement this with WebClient...toFuture on a spring-webflux project
    CompletableFuture<Beer> beerCF = supplyAsync(() -> fetchBeer("blond"));
    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka());



    CompletableFuture<DillyDilly> dillyCF = beerCF.thenCombine(vodkaCF, DillyDilly::new);
    log.info("HTTP Thread released in {} ms", currentTimeMillis() - t0);
    // ✅ HTTP Thread released immediately, but ❌ hard to maintain
    return dillyCF;
  }

  // parallel work using Virtual Threads

  @GetMapping("/parallel-virtual")
  public DillyDilly blockingOnFutures() throws InterruptedException, ExecutionException, TimeoutException {
    CompletableFuture<Beer> beerCF = supplyAsync(() -> fetchBeer("blond"), virtualExecutor);
    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka(), virtualExecutor);

    Beer beer = beerCF.get(2, SECONDS); // blocking a light VT is OK
    Vodka vodka = vodkaCF.get(2, SECONDS);
    // ❌ the tasks are NOT interrupted if: if client cancels, not if the other task fails
    // ❌ JFR profiler can't trace parents not show the subtasks
    return new DillyDilly(beer, vodka);
  }
  // Structured Concurrency (not yet production-ready in Java 21 LTS)

  @GetMapping("/parallel-scope")
  public DillyDilly structuredConcurrency() throws InterruptedException, ExecutionException, TimeoutException {
    try (var scope = new ShutdownOnFailure()) {
      Subtask<Beer> beerTask = scope.fork(() -> fetchBeer("blond")); // +1 child VT
      Subtask<Vodka> vodkaTask = scope.fork(() -> fetchVodka()); // +1 child VT

      scope.joinUntil(Instant.now().plusSeconds(10)) // block the parent VT until all children complete
          .throwIfFailed(); // throw exception if any subtasks failed
      return new DillyDilly(beerTask.get(), vodkaTask.get());
    }
  }
  private UserPreferences fetchPreferences() {
    return restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
  }

  private Vodka fetchVodka() {
    return restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class);
  }

  private Beer fetchBeer(String type) {
    return restTemplate.getForObject("http://localhost:9999/api/beer/" + type, Beer.class);
  }

  public record Beer(String type) {
  }

  public record DillyDilly(Beer beer, Vodka vodka) {
  }

  public record UserPreferences(String favoriteBeerType, boolean iceInVodka) {
  }

  public record Vodka(String type) {
  }
}

