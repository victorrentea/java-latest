package victor.training.java.virtualthread.experiments;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

@Slf4j
public class Deadlock {
  // inspired by https://blog.ydb.tech/how-we-switched-to-java-21-virtual-threads-and-got-deadlock-in-tpc-c-for-postgresql-cca2fe08d70b
  // Also see deadlock-vthread-dump.txt
  public static void main() throws InterruptedException {
    try (var virtual = newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1000; i++) {
        virtual.submit(() -> work());
      }
    }
  }

  private static final Object lock = new Object();
  static int availableConnections = 4;

  @SneakyThrows
  public static void work() {
    var connection = acquireConnection();

    log.info("using the resource");
    writeOnConnection(connection);

    releaseConnection(connection);
    log.info("done");
  }

  private static String acquireConnection() throws InterruptedException {
    log.info("acquiring...");
    synchronized (lock) {
      while (availableConnections == 0) {
        lock.wait();
      }
      availableConnections--;
      log.info("acquired connection. left = " + availableConnections);
    }
    return "connection";
  }

  private static void releaseConnection(String connection) {
    log.info("releasing...");
    synchronized (lock) {
      availableConnections++;
      lock.notifyAll();
    }
  }

  @SneakyThrows
  private static void writeOnConnection(String connection) {
    synchronized (connection) {
      log.info("writing...");
      Thread.sleep(10);
    }
  }
}
