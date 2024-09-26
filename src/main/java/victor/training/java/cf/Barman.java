package victor.training.java.cf;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class Barman {
  @Autowired
  private RestTemplate rest;
  @Autowired
  private ThreadPoolTaskExecutor poolBar;

  //  @FunctionalInterface
  interface BeerSupplier {
    Beer getBeer();
  }

  @GetMapping("/drink")
  public DillyDilly drink() {
    String beerType = "IPA";
    long t0 = currentTimeMillis();

    // Java's CompletableFuture === JavaScript/TypeScript promises Deferred/Promise, async/await
    CompletableFuture<Beer> cfBeer = supplyAsync(() -> fetchBeer(beerType))
        .exceptionally(e -> new Beer("draught beer"));
    CompletableFuture<Beer> cfWarmBeer = cfBeer.thenApply(b -> warmup(b)); // callback, when beer arrive to me from fetchBeer
    cfWarmBeer.thenAccept(b -> log.info("Drinking warm 🍺: {}", b)); // callback
    Vodka vodka = fetchVodka(); // the initial thread handling this HTTP request (coming from Tomcat in spring boot app)

    Beer beer = cfBeer.join(); // block current thread until beer is fetched > throws any exception occured during the task
    DillyDilly dilly = new DillyDilly(beer, vodka);

    // Fire-and-forget
//    try {
    CompletableFuture.runAsync(() -> auditTheDrink(dilly))
        .exceptionally(e -> { // #2 ~ of a catch with promises
          log.error("Failed to audit the drink, iwas asking for beer type " + beerType, e);
          return null;
        });

//    cfVoid.join();// stupidly block the current thread until the task is done
//    } catch (Exception e) {
//      log.error("Failed to audit the drink", e); // NEVER executes
//    }

    // Handle errors DONE

    log.info("HTTP thread blocked for {} durationMillis", currentTimeMillis() - t0);
    return dilly;
  }


  // Callback-based non-blocking concurrency
  // the bad news, the mess below will indeed non-block the black HTTP thread
  // but it will still waste 2 threads for the calls.
  // becahse the fetch beer/vodka methods are blocking inside a thread.
  // The root is that I'm using a blocking library (RestTemplate, HttpClient...)
  // to call the external services.
  @GetMapping("/drink-non-blocking")
  public CompletableFuture<DillyDilly> drinkNonBlocking() { // no .get or .join allowed
    String beerType = "IPA";
    long t0 = currentTimeMillis();
    // new CF returned so the exceptionally is not applied.
    // you should have used below in combine the value returned by .exceptionally


    // in a server application, you should never use the default thread pool
    // in which the CF run by default (ForkJoinPool.commonPool-worker-1) size=N_CPU-1 = 9 (victor)
    // . You should always provide a custom Executor to the CF factory methods
    // 1:why?
    // - Starvation: because the default thread pool is shared by all the CFs in the JVM; starve others
    // - Metadata propagation: you will lose the ThreadLocal magic metadata
    //    (Logback MDC, Spring Security Context, Open Telemetry TraceID, etc)
    //  the thread pool you submit your work should be one managed by Spring/framework
    //  that is able "lift" the metadata from parent thread to the worker thread
    // 2: how?
    // pass a Spring-managed Executor to the CF factory methods

    // Do not block threads (don't do network calls, DB calls, CPU intensive work)
    // in the default thread pool (ForkJoinPool.commonPool-worker-1)
    var beer = supplyAsync(() -> fetchBeer(beerType), poolBar)
        .exceptionally(e -> new Beer("draught beer")); // does not work because you discard the
    var vodka = supplyAsync(this::fetchVodka, poolBar);
    var dilly = beer.thenCombine(vodka, (b, v) -> new DillyDilly(b, v));

    log.info("HTTP thread blocked for {} millis", currentTimeMillis() - t0);
//    dilly.thenAccept(dilly -> httpResponse.send(toJson(dilly))) // what you can imagine the web framework will do
    return dilly;
  }


  @SneakyThrows
  // public void processUploadedFile(File) {5 mins--1h}
  public void auditTheDrink(DillyDilly dilly) {
//    try{// imagine: DB insert, kafka send, API call, takes time
    log.info("Auditing the drink: {}", dilly);
    Thread.sleep(500);
    if (true) {
      throw new RuntimeException("DB is down");
    }
    log.info("Audit done");
//    } catch (Exception e) {// #1 OK
//      log.error("Failed to audit the drink", e); // NEVER executes
//    }
  }

  private static Beer warmup(Beer beer1) {
    log.info("Warmup the beer: {}", beer1);
    return beer1;
  }

  private Vodka fetchVodka() {
    return rest.getForObject("http://localhost:9999/vodka", Vodka.class);
    // if you want to use a non-blocking http client:
    // CompletableFuture<Vodka> future = WebClient.create().get()
    //      .uri("http://localhost:9999/vodka")
    //      .retrieve()
    //      .bodyToMono(Vodka.class)// moving from the Reactive Programming☠️ world to Java8 CompletableFuture
    //      .toFuture();
    // return future; // now you can have 10K-100K requests in flight with no threads blocked.
    // the default number of threads that Tomcat will use to handle incoming requests is 200.
  }

  private Beer fetchBeer(String beerType) {
    log.info("Doing the fetchBeer for type: {}", beerType);
    String type = beerType;
    if (true) {
      throw new RuntimeException("Beer is out of stock😫😫😫😫😫");
    }
    return rest.getForObject("http://localhost:9999/beer", Beer.class);
  }
}


// CACHING should **NEVER** be the first answer to "how can I improve performance".
// Try first:
// - call less network: bring more data in one call: /beverages?types=beer,vodka ===> [Beer, Vodka]
//     GET /products?ids=1,2,3,4,5,6,7,8,9,10 or [1,2,3,4,5,6,7,8,9,10] in the body
//     <== [Product1, Product2, Product3, Product4, Product5, Product6, Product7, Product8, Product9, Product10]
// - optimize the network calls
// - parallelize your work (CPU or network) after JFR profiling your flow

// There are only 2 hard things in Computer Science:
// cache invalidation and naming things.