package victor.training.java.loom;

public class Util {
  public static void sleepq(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
