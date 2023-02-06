package victor.training.java.loom;

import victor.training.java.Util;

import java.util.List;
import java.util.stream.IntStream;

public class VirtualThread {
  public static void main(String[] args) throws InterruptedException {
    List<Thread> threads = IntStream.range(0, 5)
            .mapToObj(i -> Thread.startVirtualThread(() -> {
              String t1 = Thread.currentThread().toString();
              System.out.println(t1 + " runs Task #" + i + " - BEFORE");
              Util.sleepMillis(100);
              Thread t2 = Thread.currentThread();
              System.out.println(t2 + " runs Task #" + i + " - AFTER");
              if (!t1.equals(t2)) {
                System.err.println(" FOUND !!!");
                System.exit(1);
              }
            })).toList();

    for (Thread thread : threads) {
      thread.join();
    }
  }

}
