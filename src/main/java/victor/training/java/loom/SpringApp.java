package victor.training.java.loom;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

@SpringBootApplication
@RestController
public class SpringApp {
  private static final Logger log = LoggerFactory.getLogger(SpringApp.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
  }

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
    log.info(index + " took " + (t1-t0));

    return beer1 + " and " + beer2;
  }

  @GetMapping("/par")
  public String get() throws InterruptedException, ExecutionException {
    long t0 = currentTimeMillis();
    int index = indexCounter.incrementAndGet();
    log.info(index + " START in thread " + Thread.currentThread());
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<Beer> beer1F = scope.fork(() -> rest.getForObject("http://localhost:9999/api/beer", Beer.class));
      log.info(index + " GOT BEER 1 in thread " + Thread.currentThread());
      Future<Beer> beer2F = scope.fork(() -> rest.getForObject("http://localhost:9999/api/beer", Beer.class));
      scope.join();
      scope.throwIfFailed();
      log.info(index + " GOT BEER 2 in thread " + Thread.currentThread());
      String result = beer1F.resultNow() + " and " + beer2F.resultNow();
      long t1 = currentTimeMillis();
      log.info(index + " took " + (t1-t0));
      return result;
    }
  }
    @GetMapping("/delay")
    public String delay() {
      String th0 = Thread.currentThread().toString();
      Util.sleepq(1000);
      String th1 = Thread.currentThread().toString();
      if (!th1.equals(th0)) {
        n.incrementAndGet();
      }
      return "Started in <br>"+th0 +" then continued in <br>"+th1+"<br><br>Recorded number of platform thread shifts: " + n;
    }

  private static final AtomicInteger n = new AtomicInteger(0);


  static class Beer {
    private String type;

    public Beer() {
    }

    public Beer(String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }
  }

}
