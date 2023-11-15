package victor.training.java.virtualthread.experiments;

import victor.training.java.Util;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class OneMillion {

  public static void main(String[] args) {
    var counter = new AtomicInteger(0);
    long heap0 = getUsedMemory();
    try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1_000_000; i++) {
        virtualExecutor.submit(() -> {
          counter.incrementAndGet();
          Util.sleepMillis(5000);
          counter.decrementAndGet();
        });
      }
      System.out.println("All tasks submitted");
      Util.sleepMillis(4000);
      System.out.println("Now running threads: " + counter.get());
      long heap1 = getUsedMemory();
      System.out.println("Using Delta Memory:  " + (heap1 - heap0) / 1024 / 1024 + " MB");
      System.out.println("Using / thread:  " + (heap1 - heap0) / 1_000_000 + " bytes");

    }
    System.out.println("All threads finished: " + counter.get());
  }
  public static long getUsedMemory() {
    System.gc(); // to free the intermediary allocated [] when ArrayList grows
    return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() +
           ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed();
  }
}
