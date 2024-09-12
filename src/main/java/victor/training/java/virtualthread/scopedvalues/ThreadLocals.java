package victor.training.java.virtualthread.scopedvalues;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocals {
  public final static ThreadLocal<String> threadLocalUser =
      new ThreadLocal<>();

  public static void main() {
    threadLocalUser.set("Victor");
    log.info("Before: {}", threadLocalUser.get());
    method();
    log.info("After: {}", threadLocalUser.get());
  }

  public static void method() {
    log.info("Same thread: {}", threadLocalUser.get());
    new Thread(ThreadLocals::subtask).start();
  }

  public static void subtask() {
    log.info("Child thread: {}", threadLocalUser.get());
  }
}
