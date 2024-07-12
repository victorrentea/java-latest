package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

@Slf4j
public class Deadlock {

    // inspired by https://blog.ydb.tech/how-we-switched-to-java-21-virtual-threads-and-got-deadlock-in-tpc-c-for-postgresql-cca2fe08d70b
  public static void main() throws InterruptedException {
    try (var virtual = newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1000; i++) {
        virtual.submit(() -> entry());
      }
    }
  }

  private static final Object lock = new Object();
  static int connections = 4;

  @SneakyThrows
  public static void entry() {
    log.info("acquiring Connection from JDBC Connection Pool...");
    synchronized (lock) {
      while (connections == 0) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
        }
      }
      connections--;
      log.info("acquired. left = "+ connections);
    }

    log.info("using the resource");
    call();

    log.info("releasing...");
    synchronized (lock) {
      connections++;
      lock.notifyAll();
    }
    log.info("done");
  }

  @SneakyThrows
  private static void call() {
    Thread.sleep(10);
  }
}
