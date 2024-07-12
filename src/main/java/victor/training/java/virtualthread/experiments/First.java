package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import victor.training.java.Util;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class First {
  record ExecutionTimeframe(long start, long end) {
  }

  public static void main(String[] args) throws Exception {
    Map<Integer, ExecutionTimeframe> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      long tSubmit = currentTimeMillis();
      IntStream.range(0, 30).forEach(id ->
          executor.submit(() -> {
//            io(); // am vazut thread hoping
//            intenseCpu(); // can delay the start of other faster tasks
            long tStart = currentTimeMillis();
            synchronizedIsCppCode(); // can starve the shared OS Carrier Thread Pool
            long tEnd = currentTimeMillis();
            taskCompletionTimes.put(id, new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit));
          }));
    }

    System.out.println("Tasks started, please wait a while...");

    printExecutionTimes(taskCompletionTimes);
  }

  @SneakyThrows
  private static void io() {
    Thread.sleep(100);
    // RestTemplate.get..
    // WebClient...block()
    // CompletableFuture...get()
  }

  public static long blackHole;

  public static void intenseCpu() {
    BigInteger res = BigInteger.ZERO;
    for (int j = 0; j < 100_000_000; j++) { // decrease this number for slower machines
      res = res.add(BigInteger.valueOf(1L));
//      if (j%100000==0)Thread.yield(); // "fura-mi PT"
    }
    blackHole = res.longValue();
  }

  static int c;
  public static synchronized void synchronizedIsCppCode() {
//    synchronized (First.class) { // inseamna...
      Util.sleepMillis(100); // mai scurt sa ia timp
      c++;
//    }
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
}
