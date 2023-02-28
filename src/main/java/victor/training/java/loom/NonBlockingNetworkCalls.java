package victor.training.java.loom;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

@Slf4j
@RestController
public class NonBlockingNetworkCalls {
  private static final ThreadLocal<String> tl = new ThreadLocal<>();
  // cu thread local se propaga @Transactional, SecurityContextHolder, TraceID din Sleuth, MDC
  // inca merg pentru ca ThreadLocal e legat de thredul virtual, care nu se schimba pe durata unui task.

  private final RestTemplate rest = new RestTemplate();
  private static final AtomicInteger indexCounter = new AtomicInteger(1);

  // traditional endpointurile http lucreaza in "one thread per request" mode
  // cand vine o connex tcp la tomcat, Tomcat ia un thread dintr-un thread pool (recicleaza threaduri)
  // pentru ca sunt scump de creat threaduri noi: (tre sa sari in kernel space OS call dureaza)
  //
  @GetMapping("/beer")
  public Beer seq() {
    int requestId = indexCounter.getAndIncrement();
    tl.set("req"+requestId);
    MDC.put("reqId", "#%04d".formatted(requestId)); // %X{reqId} in application.properties
    log.info("req#{} START in thread={} (TL={})", requestId, currentThread(),tl.get());

    UserPreferences prefs = rest.getForObject("http://localhost:9999/api/user-preferences", UserPreferences.class);

    log.info("req#{} Got prefs in thread={}: {}  (TL={})", requestId, currentThread(), prefs ,tl.get());

    Beer beer = rest.getForObject("http://localhost:9999/api/beer/"+prefs.favoriteBeerType(), Beer.class);

    log.info("req#{} END Got beer in thread={}: {} (TL={})", requestId, currentThread(), beer, tl.get());

    return beer;
  }
  // workarounduri cand NU mai poti sa cresti nr de threaduri=
  //1) CompletableFuture === promise (js)
  //2) Reactive programming : Mono.zipWit

  record UserPreferences(String favoriteBeerType, boolean iceInVodka) {}

  record Beer(String type) {
  }
}
