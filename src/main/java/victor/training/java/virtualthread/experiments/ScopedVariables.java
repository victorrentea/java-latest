package victor.training.java.virtualthread.experiments;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;

@Slf4j
public class ScopedVariables {
  private static final ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
  private static final ScopedValue<String> scopedValue = ScopedValue.newInstance();

  public static void main(String[] args) {
    try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0, 2).forEach(i -> virtualExecutor.submit(() -> experiment(i)));
    }
  }

  public static void experiment(int taskId) {
    System.out.println(currentThread() + " - Start " + taskId);
    threadLocal.set("TL-" + taskId);
    ScopedValue.runWhere(scopedValue, "SV-" + taskId, () -> {
      System.out.println(currentThread() + " - Thread Local: " + threadLocal.get());
      System.out.println(currentThread() + " - Scoped Value: " + scopedValue.get());

      try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
        scope.fork(() -> subtask(taskId, 0));
        scope.fork(() -> subtask(taskId, 1));
        try {
          scope.join().throwIfFailed();
        } catch (ExecutionException | InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

  }

  private static String subtask(int taskId, int childId) {
    System.out.println(currentThread() + " - Child " + childId + ":Thread Local: " + threadLocal.get());
    System.out.println(currentThread() + " - Child " + childId + ":Scoped Value: " + scopedValue.get());
    return "ok";
  }
}
