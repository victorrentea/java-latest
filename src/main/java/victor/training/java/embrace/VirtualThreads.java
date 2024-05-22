package victor.training.java.embrace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import victor.training.java.embrace.Apis.DillyDilly;
import victor.training.java.embrace.Apis.UserPreferences;

import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;

import static java.time.Instant.now;

@RestController
@RequiredArgsConstructor
public class VirtualThreads {
  private final Apis api;

  //region Reactive-Style ☣️☠️ using Reactor/Spring, Observable/Android, Mutiny/Quarkus...
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
    UserPreferences preferences = api.fetchPreferences();
    var beer = api.fetchBeer(preferences);
    var vodka = api.fetchVodka();
    return new DillyDilly(beer, vodka);
  }
  //endregion

  //region Structured Concurrency😎 (still in preview, ready in 25🤞)
  private final ThreadPoolTaskExecutor virtualExecutor;
  @GetMapping("/dilly-scope")
  public DillyDilly drink() throws Exception {
    try (var scope = new ShutdownOnFailure()) { // ✅ if one subtask fails, the other(s) are interrupted
      var beerSubtask = scope.fork(() -> api.fetchBeer(api.fetchPreferences()));
      var vodkaSubtask = scope.fork(() -> api.fetchVodka());

      scope.joinUntil(now().plusSeconds(10)) //block until all subtasks complete (with timeout)
          .throwIfFailed();
      return new DillyDilly(beerSubtask.get(), vodkaSubtask.get());
    }// ✅ interrupting parent thread interrupts its subtasks
  }
  //endregion
}


@Service
@RequiredArgsConstructor
class Apis {
  private final RestClient restClient;

  public record UserPreferences(String favoriteBeerType) {
  }

  public UserPreferences fetchPreferences() {
    return restClient.get()
        .uri("http://localhost:9999/user/preferences")
        .retrieve()
        .body(UserPreferences.class);
  }

  public record Beer(String type) {
  }

  public Beer fetchBeer(UserPreferences pref) {
    return restClient.get()
        .uri("http://localhost:9999/beer/{}", pref.favoriteBeerType())
        .retrieve()
        .body(Beer.class);
  }


  public record Vodka(String type) {
  }

  public Vodka fetchVodka() {
    return restClient.get()
        .uri("http://localhost:9999/vodka")
        .retrieve()
        .body(Vodka.class);
  }

  public record DillyDilly(Beer beer, Vodka vodka) {
  }
}

