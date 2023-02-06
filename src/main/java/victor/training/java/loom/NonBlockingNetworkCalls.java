package victor.training.java.loom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

@RestController
public class NonBlockingNetworkCalls {
  private static final Logger log = LoggerFactory.getLogger(NonBlockingNetworkCalls.class);

  private final RestTemplate rest = new RestTemplate();
  private static final AtomicInteger indexCounter = new AtomicInteger(0);

  @GetMapping("/seq")
  public String seq() {
    long t0 = currentTimeMillis();
    int index = indexCounter.incrementAndGet();
    log.info(index + " START in thread " + Thread.currentThread());
    Beer beer1 = rest.getForObject("http://localhost:9999/api/beer", Beer.class);
    log.info(index + " GOT BEER 1 in thread " + Thread.currentThread());
    Beer beer2 = rest.getForObject("http://localhost:9999/api/beer", Beer.class);
    log.info(index + " GOT BEER 2 in thread " + Thread.currentThread());
    long t1 = currentTimeMillis();
    log.info(index + " took " + (t1 - t0));

    return beer1 + " and " + beer2;
  }


}
