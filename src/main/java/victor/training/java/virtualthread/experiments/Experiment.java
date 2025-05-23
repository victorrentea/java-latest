package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.springframework.web.client.RestTemplate;
import victor.training.java.Util;
import victor.training.java.virtualthread.util.RunMonitor;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;

import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

@Slf4j
public class Experiment {

  public static void main() throws Exception {
    Util.sleepMillis(1000); // allow the SpringApp to restart
    RunMonitor monitor = new RunMonitor();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int taskId = 0; taskId < 3; taskId++) {
        Runnable work = () -> {
//          io(); // add a prior warmup call
//          cpu();
          locks();
        };
        executor.submit(monitor.run(taskId, work));
      }

      // other parallel tasks:
//      IntStream.range(30, 40).forEach(taskId -> executor.submit(monitor(taskId, First::io)));
      System.out.println("Waiting for tasks to finish...");
    }
    monitor.printExecutionTimes();
  }

  private static final HttpClient javaClient = HttpClient.newHttpClient();
//  private static final HttpClient javaClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

  private static final RestTemplate restTemplate = new RestTemplate();

//  private static final org.apache.http.client.HttpClient apacheClient = org.apache.http.impl.client.HttpClients.createDefault();

  @SneakyThrows
  private static void io() {
    URI uri = URI.create("http://localhost:8080/call");
    Thread.sleep(100); // pretend a network call:

//    var result = restTemplate.getForObject(uri, String.class);

//    HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
//    var result = javaClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

//    HttpEntity entity = apacheClient.execute(new HttpGet(uri)).getEntity();
//    var result = EntityUtils.toString(entity);

//    log.info("Fetched {}", result); // IO operation: unmounts PT
  }
  // TODO throttle pressure on external services using a Semaphore?

  /* ============ CPU ============= */
  public static long blackHole;

  public static void cpu() {
    BigInteger res = ZERO;
    for (int j = 0; j < 10_000_000; j++) { // decrease this number for slower machines
      res = res.add(valueOf(j).sqrt());
    }
    blackHole = res.longValue();
    // TODO Fix#1: -Djdk.virtualThreadScheduler.parallelism=20
    // TODO Fix#2: Thread.yield 'every now and then'
  }

  /* ============ LOCKS ============= */
  static int sharedMutable;

  public static synchronized void locks() {
    sharedMutable++;
    Util.sleepMillis(100); // long operation (eg network) in sync block

    // TODO add to VM options: -Djdk.tracePinnedThreads=full
    // TODO run with JFR profiler and observe "Virtual Thread Pinned" events in the .jfr recording
    // TODO run ExperimentTest @Test @ShouldNotPin
    // TODO Fix: Refactor to ReentrantLock
  }

}
