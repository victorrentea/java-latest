package victor.training.java.embrace;

import io.netty.util.concurrent.ThreadPerTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.java.embrace.VirtualThreadsClient.DillyDilly;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.TimeoutException;

import static java.time.Instant.now;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VirtualThreads {
  private final VirtualThreadsClient client;
  private final ThreadPoolTaskExecutor virtualExecutor;

  //region Reactive-Style ☠️ using Reactor/Spring, Observable/Android, Mutiny/Quarkus...
  //  @GetMapping("/dilly")
  //  public Mono<DillyDilly> drinkReactive() {
  //    return Mono.zip(
  //            client.fetchUserPreferences()
  //                .flatMap(pref -> client.fetchBeer(pref)),
  //            client.fetchVodka(),
  //            (beer, vodka) -> new DillyDilly(beer, vodka))
  //        .timeout(Duration.ofSeconds(10));
  //  }
  //endregion

  //region Imperative-Style 💖
  @GetMapping("/dilly")
  public DillyDilly drinkVirtual() throws Exception {
    var beerPromise = supplyAsync(()-> client.fetchBeer(client.fetchUserPreferences()), virtualExecutor);
    var vodkaPromise = supplyAsync(() -> client.fetchVodka(),virtualExecutor);
    Beer beer = beerPromise.get();
    return new DillyDilly(beer, vodka);
  }
  //endregion

  //region Structured Concurrency (still in preview, ready in 25🤞)
  @GetMapping("/dilly-scope")
  public DillyDilly drink() throws InterruptedException, ExecutionException, TimeoutException {
    try (var scope = new ShutdownOnFailure()) {
      // spawn 2 child virtual threads:
      var beerTask = scope.fork(() -> client.fetchBeer(client.fetchUserPreferences()));
      var vodkaTask = scope.fork(() -> client.fetchVodka());

      // block until all subtasks complete
      scope.joinUntil(now().plusSeconds(10)) // ... until timeout
          .throwIfFailed(); // ... or until any child throws

      return new DillyDilly(beerTask.get(), vodkaTask.get());
    }
  }
  // ✅ readable
  // ✅ interrupted parent interrupts child subtasks
  // ✅ if one subtask fails, the other is interrupted
  //endregion


}


