package victor.training.java.loom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

@RestController
public class NonBlockingNetworkCalls {
  private static final Logger log = LoggerFactory.getLogger(NonBlockingNetworkCalls.class);

  private final RestTemplate rest = new RestTemplate();
  private static final AtomicInteger indexCounter = new AtomicInteger(0);

  @GetMapping("/beer")
  public Beer seq() {
    int requestId = indexCounter.getAndIncrement();
    log.info("Start{} in {}", requestId, currentThread());
    UserPreferences prefs = rest.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);
    log.info("Got{} prefs in {}",requestId, currentThread());
    Beer beer = rest.getForObject("http://localhost:9999/api/beer/"+prefs.favoriteBeerType(), Beer.class);
    log.info("Got{} beer in {}",requestId, currentThread());
    System.out.println("Got beer " + beer);
    return beer;
  }

}
record UserPreferences(String favoriteBeerType, boolean iceInVodka) {}

record Beer(String type) {
}
