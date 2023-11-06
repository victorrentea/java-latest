package victor.training.java.virtualthread.experiments;

import com.zaxxer.hikari.util.UtilityElf;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

public class Pinned {

  public static void main(String[] args) throws Exception {
    record Times(long start, long end) {
    }
//    ExecutorService executor = Executors.newCachedThreadPool(); // OS threads
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); // virtual threads

    Map<Integer, Times> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());

    long tSubmit = currentTimeMillis();
    IntStream.range(0, 50).forEach(id ->
        executor.submit(() -> {
          long tStart = currentTimeMillis();
          intenseCpu();
          synchronizedIsCppCode();
          long tEnd = currentTimeMillis();
          taskCompletionTimes.put(id, new Times(tStart - tSubmit, tEnd - tSubmit));
        }));

    System.out.println("CPU-bound tasks started. Please wait a while...");
    System.out.println("(you can also decrease the big number above on slower machines)");
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.MINUTES);

    long max = taskCompletionTimes.values().stream().mapToLong(Times::end).max().orElseThrow();
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      Times t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = "#".repeat((int) ((t.end() - t.start()) * r));
      System.out.printf("Task %02d: %s%s%n", taskId, spaces, action);
    }
  }

  public static long blackHole;
  public static void intenseCpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 100_000_000; j++) {
      res = res.add(BigInteger.valueOf(1L));
    }
    blackHole = res.longValue();
  }

  public static void synchronizedIsCppCode() {
    synchronized (Pinned.class) {
      UtilityElf.quietlySleep(1000);
    }
  }
}
