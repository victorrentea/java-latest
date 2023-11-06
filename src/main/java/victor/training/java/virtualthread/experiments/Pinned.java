package victor.training.java.virtualthread.experiments;

import com.zaxxer.hikari.util.UtilityElf;
import victor.training.java.Util;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

public class Pinned {
  record ExecutionTimeframe(long start, long end) {
  }

  public static void main(String[] args) throws Exception {
    Map<Integer, ExecutionTimeframe> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());

//    ExecutorService executor = Executors.newCachedThreadPool(); // OS threads
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); // virtual threads

    long tSubmit = currentTimeMillis();
    IntStream.range(0, 50).forEach(id ->
        executor.submit(() -> {
          long tStart = currentTimeMillis();
          intenseCpu(); // can delay the start of other faster tasks
//          synchronizedIsCppCode(); // can starve the shared OS Carrier Thread Pool
          long tEnd = currentTimeMillis();
          taskCompletionTimes.put(id, new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit));
        }));

    System.out.println("Tasks started, please wait a while...");
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);

    printExecutionTimes(taskCompletionTimes);
  }

  private static void printExecutionTimes(Map<Integer, ExecutionTimeframe> taskCompletionTimes) {
    long max = taskCompletionTimes.values().stream().mapToLong(ExecutionTimeframe::end).max().orElseThrow();
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      ExecutionTimeframe t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = "#".repeat((int) ((t.end() - t.start()) * r));
      System.out.printf("Task %02d: %s%s%n", taskId, spaces, action);
    }
  }

  public static long blackHole;
  public static void intenseCpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 100_000_000; j++) { // decrease this number for slower machines
      res = res.add(BigInteger.valueOf(1L));
    }
    blackHole = res.longValue();
  }

  public static void synchronizedIsCppCode() {
    synchronized (Pinned.class) {
      Util.sleepMillis(100);
    }
  }
}
