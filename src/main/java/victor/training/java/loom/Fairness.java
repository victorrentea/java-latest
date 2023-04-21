package victor.training.java.loom;

import com.zaxxer.hikari.util.UtilityElf;
import org.jooq.lambda.tuple.Tuple2;

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

import static java.lang.System.currentTimeMillis;

public class Fairness {
  public static long blackHole;

  public static void main(String[] args) throws Exception {
//    ExecutorService executor = Executors.newCachedThreadPool();
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    Map<Integer, Times> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());

    long tSubmit = currentTimeMillis();
    for (int i = 0; i < 50; i++) {
      Instant start = Instant.now();
      int id = i;

      executor.submit(() -> {
        long tStart = currentTimeMillis();
        BigInteger res = BigInteger.ZERO;

        //A
        for (int j = 0; j < 100_000_000; j++) {
          res = res.add(BigInteger.valueOf(1L));
        }

        // B
//        synchronized (Fairness.class) {
//          UtilityElf.quietlySleep(1000);
//        }

        blackHole = res.longValue();

        long tEnd = currentTimeMillis();
        taskCompletionTimes.put(id, new Times((int) (tStart - tSubmit), (int) (tEnd - tSubmit)));
      });
    }

    System.out.println("CPU-bound tasks started. Please wait a while...");
    System.out.println("(you can also decrease the big number above on slower machines)");
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.HOURS);

    int max = taskCompletionTimes.values().stream().mapToInt(Times::end).max().orElseThrow();
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      Times t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = "#".repeat((int) ((t.end() - t.start()) * r));
      System.out.printf("Task %02d: %s%s%n", taskId, spaces, action);
    }
  }
  record Times(int start, int end){}
}
