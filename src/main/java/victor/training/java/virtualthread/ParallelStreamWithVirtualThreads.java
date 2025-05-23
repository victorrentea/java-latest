package victor.training.java.virtualthread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelStreamWithVirtualThreads {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();
    pool.submit(() -> {
      numbers.parallelStream()
          .forEach(x -> {
            System.out.println("Thread: " + Thread.currentThread() + " " + x);
          });
    }).get();


  }
}
