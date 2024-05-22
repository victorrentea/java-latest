package victor.training.java.embrace;

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
    var beer = client.fetchBeer(client.fetchUserPreferences());
    var vodka = client.fetchVodka();
    return new DillyDilly(beer, vodka);
  }
  //endregion

  //region Structured Concurrency (still in preview, ready in 25🤞)
  @GetMapping("/dilly-scope")
  public DillyDilly drink() throws Exception {
    try (var scope = new ShutdownOnFailure()) { // ✅ if one subtask fails, the other(s) are interrupted
      var beerSubtask = scope.fork(() -> client.fetchBeer(client.fetchUserPreferences()));
      var vodkaSubtask = scope.fork(() -> client.fetchVodka());

      scope.joinUntil(now().plusSeconds(10)) // // block until all subtasks complete (with timeout)
          .throwIfFailed();
      return new DillyDilly(beerSubtask.get(), vodkaSubtask.get());
    }// ✅ interrupting parent threads interrupts its subtasks
  }
  //endregion


}


