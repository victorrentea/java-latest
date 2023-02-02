package victor.training.java.loom;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class VirtualExecutor {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    record Task(int i) implements Callable<Integer> {
      public Integer call() throws InterruptedException {
        System.out.println(Thread.currentThread() + " runs Task #" + i + " - BEFORE");
        Thread.sleep(100);
        System.out.println(Thread.currentThread() + " runs Task #" + i + " - AFTER");
        return i;
      }
    }

    List<Task> tasks = IntStream.range(0, 5).mapToObj(Task::new).toList();

    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    List<Future<Integer>> futures = executor.invokeAll(tasks);
    for (Future<Integer> future : futures) {
      System.out.println("Got: " + future.get());
    }
  }

}
