package victor.training.java.loom;

import jdk.incubator.concurrent.StructuredTaskScope;
import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

// https://openjdk.org/jeps/428
@RestController
public class StructuredConcurrency {
  private static final Logger log = LoggerFactory.getLogger(StructuredConcurrency.class);

  private final RestTemplate rest = new RestTemplate();
  private static final AtomicInteger indexCounter = new AtomicInteger(0);

  // ADD TO THE VM OPTIONS: --add-modules jdk.incubator.concurrent

  @GetMapping("/par")
  public String get() throws InterruptedException, ExecutionException {
    long t0 = currentTimeMillis();
    int index = indexCounter.incrementAndGet();
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<Beer> beer1F = scope.fork(() -> fetchBeer());
      Future<Beer> beer2F = scope.fork(() -> fetchBeer());

      scope.join(); // Join both forks
      scope.throwIfFailed(); // ... and propagate errors

      // Here, both forks have succeeded, so compose their results
      String result = "Request #"+index+ " completed in " + (currentTimeMillis() - t0) + ": " + beer1F.resultNow() + " and " + beer2F.resultNow();
      return result;
    }
  }

  private Beer fetchBeer() {
    return rest.getForObject("http://localhost:9999/api/beer", Beer.class);
  }
}
