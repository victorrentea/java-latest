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
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class First {
  record ExecutionTimeframe(long start, long end, char symbol) {
  }

  public static void main(String[] args) throws Exception {
    Map<Integer, ExecutionTimeframe> taskCompletionTimes = Collections.synchronizedMap(new TreeMap<>());
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      long tSubmit = currentTimeMillis();
      IntStream.range(0, 30).forEach(id ->
          executor.submit(() -> {
            long tStart = currentTimeMillis();
            String startThread = Thread.currentThread().toString();
            synchronizedIsCppCode(); // can starve the shared OS Carrier Thread Pool
            String endThread = Thread.currentThread().toString();
            long tEnd = currentTimeMillis();
            if (!startThread.equals(endThread)) {
              log.warn("OS THREAD HOP DETECTED: Task #" + id + " started in \n" + startThread + "\nbut ended in\n" + endThread + "\n");
            }
            taskCompletionTimes.put(id, new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit, '#'));
          }));
      IntStream.range(30, 40).forEach(id ->
          executor.submit(() -> {
            long tStart = currentTimeMillis();
            io();// acest IO inocentn care ar trebui sa profite de VTs
            // sta ca prostu ca toate PT sunt lipite de un VT blocate in syncronized
            long tEnd = currentTimeMillis();
            taskCompletionTimes.put(id, new ExecutionTimeframe(tStart - tSubmit, tEnd - tSubmit, '*'));
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
  // daca un VT asteapta sa intre in aceasta metoda (sa ia monitorul)
  // JVM nu poate unmount VT de pe PT => "thread pinning"
  // veste buna: se vede in JFR output daca incarci.jfr in JDK Mission Control
  private static ReentrantLock lock = new ReentrantLock();
  public static  void synchronizedIsCppCode() {
    lock.lock();
    try { // imediat dupa lock(), cu .unlock in finally {}
      ceva();
    } finally {
      lock.unlock();
    }
  }

  private static void ceva() {
    if (true) throw new RuntimeException("Intentional");
    Util.sleepMillis(100); // mai scurt sa ia timp
  }

  private static void printExecutionTimes(Map<Integer, ExecutionTimeframe> taskCompletionTimes) {
    long max = taskCompletionTimes.values().stream().mapToLong(ExecutionTimeframe::end).max().orElseThrow();
    double r = 50d / max;
    for (Integer taskId : taskCompletionTimes.keySet()) {
      ExecutionTimeframe t = taskCompletionTimes.get(taskId);
      String spaces = " ".repeat((int) (t.start() * r));
      String action = (""+t.symbol).repeat((int) ((t.end() - t.start()) * r));
      System.out.printf("Task %02d: %s%s%n", taskId, spaces, action);
    }
  }
}
