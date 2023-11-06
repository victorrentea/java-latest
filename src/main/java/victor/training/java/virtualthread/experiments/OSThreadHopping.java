package victor.training.java.virtualthread.experiments;

import lombok.extern.slf4j.Slf4j;
import victor.training.java.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

@Slf4j
public class OSThreadHopping {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) { // +1 thread pt fiecare task
      IntStream.range(0, 5).forEach(i -> virtualExecutor.submit(() -> blockingWork(i)));
    }
    log.info("All tasks completed");
  }

  private static void blockingWork(int taskId) {
    String startThread = currentThread().toString();
    log.info("Task #{} START", taskId);
    Util.sleepMillis(100); // causes JVM to unpin the virtual thread from OS carrier thread
    String endThread = currentThread().toString();
    log.info("Task #{} END", taskId);

    if (!startThread.equals(endThread)) {
      log.warn("OS THREAD HOP DETECTED: Task #" + taskId + " started in \n" + startThread + "\nbut ended in\n" + endThread + "\n");
    }
  }
}
