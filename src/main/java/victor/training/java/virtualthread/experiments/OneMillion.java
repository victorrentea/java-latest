package victor.training.java.virtualthread.experiments;

import com.sun.management.OperatingSystemMXBean;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newThreadPerTaskExecutor;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

public class OneMillion {

  public static final int TOTAL = 1000_000;

  public static void main(String[] args) throws IOException, InterruptedException {
    var counter = new AtomicInteger(0);
    long mem0 = getUsedMemory();
    CountDownLatch pause = new CountDownLatch(1);
    CountDownLatch started = new CountDownLatch(TOTAL);
    try (var virtual = newVirtualThreadPerTaskExecutor()) {
//    try (var virtual = newThreadPerTaskExecutor(Thread::new)) {
      for (int i = 0; i < TOTAL; i++) {
        virtual.submit(new Runnable() {
          @Override
          @SneakyThrows
          public void run() {
            counter.incrementAndGet();
            started.countDown();
            pause.await();
            counter.decrementAndGet();
          }
        });
      }
      System.out.println("Waiting for all tasks to start...");
      started.await();
      System.out.println("All tasks submitted");
      System.out.println("Now running threads: " + counter.get());
      long mem1 = getUsedMemory();
      System.out.println("Delta Memory:  " + (mem1 - mem0) / 1024 + " KB, init=" + mem0 / 1024 + " KB");
      System.out.println("Delta / thread:  " + (mem1 - mem0) / TOTAL + " bytes");
      System.out.println("processID (PID) =" + ManagementFactory.getRuntimeMXBean().getName());
      System.out.println("Hit Enter to end");
      System.in.read();
      pause.countDown();
    }
    System.out.println("All threads ended: " + counter.get());
  }

  @SneakyThrows
  public static long getUsedMemory() {
//    System.gc(); // to free the intermediary allocated [] when ArrayList grows
    sleep(10);
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }
}
