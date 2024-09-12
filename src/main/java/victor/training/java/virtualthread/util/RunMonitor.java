package victor.training.java.virtualthread.util;

import org.apache.tomcat.util.threads.VirtualThreadExecutor;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;


import static java.lang.System.currentTimeMillis;
import static victor.training.java.virtualthread.util.DisplayVTNameInLogback.friendlyVTName;

public class RunMonitor {
  private final Map<Integer, ExecutionTimeframe> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());
  private final long tSubmit = currentTimeMillis();

  // --------- supporting code ------------
  public Runnable run(int taskId, Runnable runnable) {
    return () -> {
      long tStart = currentTimeMillis();
      String startThreadName = Thread.currentThread().toString();
      runnable.run();
      String endThreadName = Thread.currentThread().toString();
      long tEnd = currentTimeMillis();
      String hop = startThreadName.equals(endThreadName) ? "❌No hop" :
          "✅Hopped from " + friendlyVTName(startThreadName)
          + " to " + friendlyVTName(endThreadName);
      var prev = taskCompletionTimes.put(taskId,
          new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit, '*', hop));
      if (prev != null) {
        throw new IllegalArgumentException("Task ID already exists: " + taskId);
      }
    };
  }

  public void printExecutionTimes() {
    long max = taskCompletionTimes.values().stream().mapToLong(ExecutionTimeframe::end).max()
        .orElseThrow(() -> new IllegalStateException("Tasks finished too fast! Please add more work."));
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      ExecutionTimeframe t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = ("" + t.symbol).repeat((int) ((t.end() - t.start()) * r));
      String trail = " ".repeat(50 - spaces.length() - action.length() - 1);
      System.out.printf("Task %02d: %s%s%s ->> %s%n", taskId, spaces, action,trail, t.hop);
    }
    System.out.printf("On a machine with %d CPUs%n", Runtime.getRuntime().availableProcessors());
  }

  record ExecutionTimeframe(long start, long end, char symbol, String hop) {
  }
}
