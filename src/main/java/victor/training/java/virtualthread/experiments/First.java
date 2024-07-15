package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import victor.training.java.Util;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class First {
  record ExecutionTimeframe(long start, long end, char symbol, String hop) {
  }

  static Map<Integer, ExecutionTimeframe> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());
  static long tSubmit = currentTimeMillis();

  public static void main() throws Exception {
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0, 30).forEach(taskId ->
          executor.submit(monitor(taskId, () -> {
            io();
//            cpu();
//            locks();
          })));

      // other parallel tasks:
//      IntStream.range(30, 40).forEach(taskId -> executor.submit(monitor(taskId, First::io)));
      System.out.println("Waiting for tasks to finish...");
    }
    printExecutionTimes();
  }

  @SneakyThrows
  private static void io() {
    Thread.sleep(100); // standin for a network call:
    // RestTemplate.get, WebClient...block(), HttpClient, FeignClient ...
    // CompletableFuture...get()
    // TODO throttle load on external services using a Semaphore
  }

  public static long blackHole;
  public static void cpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 10_000_000; j++) { // decrease this number for slower machines
      res = res.add(BigInteger.valueOf(j).sqrt());
    }
    blackHole = res.longValue();
    // TODO Fix#1: -Djdk.virtualThreadScheduler.parallelism=20
    // TODO Fix#2: Thread.yield every now and then
  }
  static int c;

  public static synchronized void locks() {
    Util.sleepMillis(100);
    c++;
    // TODO observe Thread Pinning in the JFR recording studied using JMC
    // TODO Move to ReentrantLock
  }

  // --------- supporting code ------------
  static Runnable monitor(int taskId, Runnable runnable) {
    return () -> {
      long tStart = currentTimeMillis();
      String startThreadName = Thread.currentThread().toString();
      runnable.run();
      String endThreadName = Thread.currentThread().toString();
      long tEnd = currentTimeMillis();
      String hop = startThreadName.equals(endThreadName) ? "❌No hop" : "✅Hopped from " + startThreadName + " to " + endThreadName;
      var prev = taskCompletionTimes.put(taskId,
          new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit, '*', hop));
      if (prev != null) {
        throw new IllegalArgumentException("Task ID already exists: " + taskId);
      }
    };
  }

  private static void printExecutionTimes() {
    long max = taskCompletionTimes.values().stream().mapToLong(ExecutionTimeframe::end).max().orElseThrow();
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      ExecutionTimeframe t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = ("" + t.symbol).repeat((int) ((t.end() - t.start()) * r));
      String trail = " ".repeat(50 - spaces.length() - action.length() - 1);
      System.out.printf("Task %02d: %s%s%s >> %s%n", taskId, spaces, action,trail, t.hop);
    }
  }
}
