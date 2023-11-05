package victor.training.java.virtualthread;

import lombok.extern.slf4j.Slf4j;
import victor.training.java.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class OSThreadHopping {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0, 5).forEach(i -> virtualExecutor.submit(() -> blockingWork(i)));
    }
    log.info("All tasks completed");
  }

  private static void blockingWork(int taskId) {
    String t1 = Thread.currentThread().toString();
    log.info(t1 + " runs Task #" + taskId + " - START");
    Util.sleepMillis(100); // causes the virtual thread to unpin from OS carrier thread
    String t2 = Thread.currentThread().toString();
    log.info(t2 + " runs Task #" + taskId + " - END");

    if (!t1.equals(t2)) {
      log.warn("OS THREAD HOP FOUND: Task #" + taskId + " started in \n" + t1 + "\nbut ended in\n" + t2 + "\n");
    }
  }

}
