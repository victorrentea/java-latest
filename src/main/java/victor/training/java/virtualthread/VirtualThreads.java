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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VirtualThreads {
  private final RestTemplate restTemplate;

  @GetMapping("/dilly-cf")
  public CompletableFuture<DillyDilly> drinkPromise() {
    CompletableFuture<UserPreferences> preferencesCF = supplyAsync(() -> fetchPrefs());

    CompletableFuture<Beer> beerCF = preferencesCF.thenApply(pref -> fetchBeer(pref));

    CompletableFuture<Vodka> vodkaCF = supplyAsync(() -> fetchVodka());

    CompletableFuture<DillyDilly> dillyCF = beerCF.thenCombine(vodkaCF, DillyDilly::new)
        .orTimeout(10, SECONDS);

    dillyCF.thenAccept(dilly -> log.info("Returning: {}", dilly));
    return dillyCF;
  }

  private static final AtomicInteger counter = new AtomicInteger();
  @GetMapping("/dilly")
  public DillyDilly drinkVirtual() {
    int id = counter.incrementAndGet();
    log.info("Start task{} in {}", id, Thread.currentThread());

    UserPreferences pref = fetchPrefs();
    log.info("Got prefs task{} in {}", id, Thread.currentThread());

    Beer beer = fetchBeer(pref);
    log.info("Got beer task{} in {}", id, Thread.currentThread());

    Vodka vodka = fetchVodka();
    log.info("Got vodka task{} in {}", id, Thread.currentThread());

    return new DillyDilly(beer, vodka);
  }

  private UserPreferences fetchPrefs() {
    return restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
  }

  private Beer fetchBeer(UserPreferences pref) {
    return restTemplate.getForObject("http://localhost:9999/api/beer/{0}", Beer.class, pref.favoriteBeerType());
  }

  private Vodka fetchVodka() {
    return restTemplate.getForObject("http://localhost:9999/api/vodka", Vodka.class);
  }


  //region Structured Concurrency (NOT yet LTS)
  @GetMapping("/dilly-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    UserPreferences pref = restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      var beerTask = scope.fork(() -> restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class)); // +1 child virtual thread
      var vodkaTask = scope.fork(() -> restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class)); // +1 child virtual thread

      scope.joinUntil(Instant.now().plusSeconds(10)) // block the parent virtual thread until all child subtasks complete
          .throwIfFailed(); // throw exception if any subtasks failed

      return new DillyDilly(beerTask.get(), vodkaTask.get());
    }
  }
  // ✅ Memory-efficient, as virtual threads are very cheap
  // ✅ easy to read
  // ✅ client cancellation cancels subtasks
  // ✅ if one subtask fails, the other is interrupted
  // ✅ JFR profiler can link children threads with parent thread
  //endregion

}

