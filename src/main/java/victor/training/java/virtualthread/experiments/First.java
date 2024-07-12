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
      IntStream.range(0, 12).forEach(id ->
          executor.submit(() -> {
            long tStart = currentTimeMillis();
            log.info("Start");
            log.info("End");
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
    }
    blackHole = res.longValue();
  }

  public static void synchronizedIsCppCode() {
    synchronized (First.class) {
      Util.sleepMillis(100);
    }
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
