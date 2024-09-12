package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import victor.training.java.Util;
import victor.training.java.virtualthread.util.RunMonitor;

import java.math.BigInteger;
import java.util.concurrent.Executors;

@Slf4j
public class Experiment {

  public static void main() throws Exception {
    RunMonitor monitor = new RunMonitor();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int taskId = 0; taskId < 30; taskId++) {
        Runnable work = () -> {
        io(); // add a prior warmup call
//          cpu();
//          locks();
        };
        executor.submit(monitor.run(taskId, work));
      }

      // other parallel tasks:
//      IntStream.range(30, 40).forEach(taskId -> executor.submit(monitor(taskId, First::io)));
      System.out.println("Waiting for tasks to finish...");
    }
    monitor.printExecutionTimes();
  }

  /* ============ IO ============= */
  private static final RestTemplate restTemplate = new RestTemplate();

  @SneakyThrows
  private static void io() {
//    Thread.sleep(100); // pretend a network call:
    var r = restTemplate.getForObject("http://localhost:8080/call", String.class);
//    System.out.println("Fetched " + r);
    // RestTemplate.get, WebClient...block(), HttpClient, FeignClient ...
    // CompletableFuture...get()
    // TODO throttle load on external services using a Semaphore
  }

  /* ============ CPU ============= */
  public static long blackHole;

  public static void cpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 10_000_000; j++) { // decrease this number for slower machines
      res = res.add(BigInteger.valueOf(j).sqrt());
    }
    blackHole = res.longValue();
    // TODO Fix#1: -Djdk.virtualThreadScheduler.parallelism=20
    // TODO Fix#2: Thread.yield 'every now and then'
  }

  /* ============ LOCKS ============= */
  static int sharedMutable;

  public static synchronized void locks() {
    Util.sleepMillis(100);
    sharedMutable++;
    // TODO observe Thread Pinning in the JFR recording studied using JMC
    // TODO Move to ReentrantLock
  }

}
