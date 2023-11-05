package victor.training.java.loom;

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
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

// https://openjdk.org/jeps/428
@RestController
public class StructuredConcurrency {
  private static final Logger log = LoggerFactory.getLogger(StructuredConcurrency.class);

  private final RestTemplate restTemplate = new RestTemplate();
  private static final AtomicInteger indexCounter = new AtomicInteger(0);

  // ADD TO THE VM OPTIONS: --add-modules jdk.incubator.concurrent

  @GetMapping("/par")
  public String get() throws InterruptedException, ExecutionException {
    long t0 = currentTimeMillis();
    int index = indexCounter.incrementAndGet();

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {

      // child thread #1
      StructuredTaskScope.Subtask<Beer> beerF = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/beer", Beer.class)); // spawns +1 child virtual thread

      // child thread #2
      StructuredTaskScope.Subtask<Vodka> vodkaF = scope.fork(() ->
          restTemplate.getForObject("http://localhost:9999/vodka", Vodka.class)); // spawns +1 child virtual thread

      scope.join(); // Join both forks, blocking the 'parent' virtual thread (very cheap)
      scope.throwIfFailed(); // if any child thread threw, this throws exception

      // Both forks have succeeded, so compose their results
      Beer beer = beerF.get();
      Vodka vodka = vodkaF.get();
      String result = "Request #"+index+ " completed in " + (currentTimeMillis() - t0) + ": " + beer + " and " + vodka;
      return result;
    }
  }

}
