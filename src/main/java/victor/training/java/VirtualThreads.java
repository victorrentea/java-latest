package victor.training.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@SpringBootApplication
public class VirtualThreads {
  private static final AtomicInteger indexCounter = new AtomicInteger(1);
  private static final ThreadLocal<String> tl = new ThreadLocal<>();

  private final RestTemplate rest = new RestTemplate();

  @GetMapping("/beer")
  public Beer beer() {
//    int requestId = indexCounter.getAndIncrement();
//    tl.set("req"+requestId);
//    MDC.put("reqId", "#%04d".formatted(requestId)); // %X{reqId} in application.properties
//    log.info("req#{} START in thread={} (TL={})", requestId, currentThread(),tl.get());

    UserPreferences prefs = rest.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);

//    log.info("req#{} Got prefs in thread={}: {}  (TL={})", requestId, currentThread(), prefs ,tl.get());

    Beer beer = rest.getForObject("http://localhost:9999/api/beer/"+prefs.favoriteBeerType(), Beer.class);

//    log.info("req#{} END Got beer in thread={}: {} (TL={})", requestId, currentThread(), beer, tl.get());
    return beer;
  }

  record UserPreferences(String favoriteBeerType, boolean iceInVodka) {}

  record Beer(String type) {
  }

  public static void main(String[] args) {
    SpringApplication.run(VirtualThreads.class, args);
  }

//  @Bean
//  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
//    // tell Tomcat to create a new virtual thread for every incoming request
//    return protocolHandler -> protocolHandler.setExecutor(
//        Executors.newVirtualThreadPerTaskExecutor());
//  }
}
