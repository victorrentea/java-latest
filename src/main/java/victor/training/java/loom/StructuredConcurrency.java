package victor.training.java.loom;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.java.loom.NonBlockingNetworkCalls.Beer;
import victor.training.java.loom.NonBlockingNetworkCalls.Vodka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

@RestController
@Slf4j
public class StructuredConcurrency {
  private final RestTemplate restTemplate = new RestTemplate();

  @GetMapping("/par")
  public String get() throws InterruptedException, ExecutionException {
    long t0 = currentTimeMillis();

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<Beer> beerTask = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/beer", Beer.class)); // spawns +1 child virtual thread

      Subtask<Vodka> vodkaTask = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class)); // spawns +1 child virtual thread

      scope.join() // Blocking the parent virtual thread until all children threads complete
        .throwIfFailed(); // throw exception if any of the child threads completed in error

      // Combine tasks' results
      Beer beer = beerTask.get();
      Vodka vodka = vodkaTask.get();
      String result = "Request completed in " + (currentTimeMillis() - t0) + ": " + beer + " and " + vodka;
      return result;
    }
  }

}
