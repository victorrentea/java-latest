package victor.training.java.virtualthread.scoped;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocals {
  public final static ThreadLocal<String> threadLocalUser = new ThreadLocal<>();

  public static void main() {
    threadLocalUser.set("Victor");
    method();
    log.info("after{}", threadLocalUser.get());
  }

  public static void method() {
    log.info("in task={}", threadLocalUser.get());
    new Thread(ThreadLocals::subtask).start();
  }

  public static void subtask() {
    log.info("subtask={}", threadLocalUser.get());
  }
}
