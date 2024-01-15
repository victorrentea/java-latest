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
  public CompletableFuture<DillyDilly> drinkCF() throws Exception {
    var preferencesCF = supplyAsync(() ->
        restTemplate.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class));

    var beerCF = preferencesCF.thenApply(pref ->
        restTemplate.getForObject("http://localhost:9999/api/beer/" + pref.favoriteBeerType(), Beer.class));

    var vodkaCF = supplyAsync(() ->
        restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class));

    var dillyCF = beerCF.thenCombine(vodkaCF, DillyDilly::new);

    dillyCF.thenAccept(dilly -> log.info("Returning: {}", dilly));
    return dillyCF;
  }
  // ✅ Memory-efficient, because HTTP Thread released immediately
  // ❌ hard to read
  // ❌ client cancellation does not propagate upstream
  // ❌ if one subtask fails, the other is NOT interrupted
  // ❌ JFR profiler cannot link children threads with parent thread

  //region Structured Concurrency (NOT yet production-ready in Java 21 LTS)
  @GetMapping("/drink-scope")
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

