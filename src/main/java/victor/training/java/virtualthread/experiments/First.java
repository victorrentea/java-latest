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
    Thread.sleep(100); // standin for:
    // RestTemplate.get..
    // WebClient...block()
    // CompletableFuture...get()
    // TODO throttle load on external services
  }

  public static long blackHole;

  public static void cpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 100_000_000; j++) { // decrease this number for slower machines
      res = res.add(BigInteger.valueOf(j).sqrt());
    }
    blackHole = res.longValue();
  }
  static int c;

  public static synchronized void locks() {
    Util.sleepMillis(100);
    c++;
  }

  // --------- supporting code ------------
  static Runnable monitor(int taskId, Runnable runnable) {
    return () -> {
      long tStart = currentTimeMillis();
      String startThreadName = Thread.currentThread().toString();
      runnable.run();
      String endThreadName = Thread.currentThread().toString();
      long tEnd = currentTimeMillis();
      String hop = startThreadName.equals(endThreadName) ? " >>" : " >> Hop detected from " + startThreadName + " to " + endThreadName;
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
      System.out.printf("Task %02d: %s%s%n", taskId, spaces, action);
    }
  }
}
