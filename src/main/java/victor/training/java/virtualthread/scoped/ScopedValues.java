package victor.training.java.virtualthread.scoped;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;

import static java.util.concurrent.Executors.callable;

@Slf4j
public class ScopedValues {
  public final static ScopedValue<String> scopedUser =
      ScopedValue.newInstance();

  public static void main() {
    ScopedValue.where(scopedUser, "Victor").run(() -> {
      method();
      log.info("after={}", scopedUser.get());
    });
  }

  @SneakyThrows
  public static void method() {
    log.info("in task={}", scopedUser.get());
//    new Thread(ScopedValues::subtask).start();

    try (var scope = new ShutdownOnSuccess()) {
      scope.fork(callable(ScopedValues::subtask));
      scope.fork(callable(ScopedValues::subtask));
      scope.join();
    }
  }

  public static void subtask() {
    log.info("subtask={}", scopedUser.get());
    log.info("in " + Thread.currentThread().toString());

  }
}
