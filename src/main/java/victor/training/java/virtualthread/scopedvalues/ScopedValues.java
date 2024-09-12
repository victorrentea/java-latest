package victor.training.java.virtualthread.scopedvalues;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;

import static java.util.concurrent.Executors.callable;

@Slf4j
public class ScopedValues {
  public final static ScopedValue<String> scopedUser = ScopedValue.newInstance();

  public static void main() {
    ScopedValue.where(scopedUser, "Victor").run(() -> {
      log.info("Before: {}", scopedUser.get());
      method();
      log.info("After: {}", scopedUser.get());
    });
  }

  @SneakyThrows
  public static void method() {
    log.info("Same thread: {}", scopedUser.get());
    try (var scope = new ShutdownOnSuccess<>()) {
      scope.fork(callable(ScopedValues::subtask));
      scope.fork(callable(ScopedValues::subtask));
      scope.join();
    }
  }
  public static void subtask() {
    log.info("Child thread ({}): {}",Thread.currentThread(), scopedUser.get());
  }
}
