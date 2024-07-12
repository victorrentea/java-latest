package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

@Slf4j
public class SynchronizedPinning {

  private static long t0;

  public static void main() throws InterruptedException {
    try (var virtual = newVirtualThreadPerTaskExecutor()) {
      t0 = System.currentTimeMillis();
      for (int i = 0; i < 100; i++) {
        virtual.submit(() -> entry());
        virtual.submit(() -> otherTask());
      }
    }
    System.out.println(counter);
    System.out.println("Took: " + (System.currentTimeMillis() - t0));
  }

  private static void otherTask() {
    log.info("Start : " + (System.currentTimeMillis() - t0));
  }
  private static final ReentrantLock lock = new ReentrantLock();
  private static final Semaphore semaphore = new Semaphore(1);

  @SneakyThrows
  public static void entry() {
    log.info("entry ");
    synchronized (SynchronizedPinning.class) {
      counter += call();
    }
    log.info("exit ");
  }

  static int counter = 0;

  @SneakyThrows
  private static int call() {
    Thread.sleep(1);
    return 1;
  }
}
