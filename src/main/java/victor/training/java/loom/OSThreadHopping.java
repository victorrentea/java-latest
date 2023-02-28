package victor.training.java.loom;

import victor.training.java.Util;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OSThreadHopping {


  public static void main(String[] args) throws InterruptedException, ExecutionException {
    newThread();
    //    virtualThreadExecutor();
  }

  private static void virtualThreadExecutor() throws InterruptedException, ExecutionException {
    try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

      List<? extends Future<?>> futures = IntStream.range(0, 5)
              .mapToObj(i -> virtualExecutor.submit(() -> theMethod(i)))
              .toList();
      for (Future<?> future : futures) {
        future.get();
      }
    }
  }

  private static void newThread() throws InterruptedException {
    List<Thread> threads = IntStream.range(0, 5)
            .mapToObj(i -> Thread.startVirtualThread(() -> theMethod(i)))
            .toList();

    for (Thread thread : threads) {
      thread.join(); // astept sa se termine
    }
  }

  private static void theMethod(int id) {
    String t1 = Thread.currentThread().toString();
    System.out.println(t1 + " runs Task #" + id + " - START");
    Util.sleepMillis(100); // simulat un Network call / DB call / File read
    String t2 = Thread.currentThread().toString();
    System.out.println(t2 + " runs Task #" + id + " - END");

//    if (!t1.equals(t2)) {
//      System.err.println("OS THREAD HOP FOUND: Task #" + id + " started in \n" + t1 + "\nbut ended in\n" + t2 + "\n");
//    }
  }

}
