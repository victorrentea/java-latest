package victor.training.java.loom;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Fairness {
  public static long blackHole;

  public static void main(String[] args) throws Exception {
//    ExecutorService executor = Executors.newCachedThreadPool();
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    Map<Integer, Long> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());

    for (int i = 0; i < 50; i++) {
      Instant start = Instant.now();
      int id = i;

      executor.submit(() -> {
        BigInteger res = BigInteger.ZERO;

        for (int j = 0; j < 100_000_000; j++) {
          res = res.add(BigInteger.valueOf(1L));
        }

        blackHole = res.longValue();

        taskCompletionTimes.put(id, Duration.between(start, Instant.now()).toMillis());
      });
    }

    System.out.println("CPU-bound tasks started. Please wait a while...");
    System.out.println("(you can also decrease the big number above on slower machines)");
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.HOURS);

    for (Integer taskId : taskCompletionTimes.keySet()) {
      System.out.printf("Task %02d took %d ms%n", taskId, taskCompletionTimes.get(taskId));
    }
  }
}
