package victor.training.java.patterns.proxy;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ThreadUtils;

import java.util.function.Supplier;

import static java.time.Duration.ofSeconds;

public class ExecuteAroundPattern {
  public static void main(String[] args) {
    int result = measureTime(  ()   -> sum(2,3)     );
    System.out.println("Computed: " + result);

    int multiplyResult = measureTime(  ()   -> multiply(2,3)     );
    System.out.println("Computed: " + multiplyResult);

    // private final MeterRegistry meterRegistry; // micrometer = standard in Java to report metrics
    //
    //  public void method() {
    //    log.info("Doing something");
    //    meterRegistry.timer("my.timer").record(() -> {
    //      log.info("Doing something expensive");
    //    });
    //  }
  }


  public static <T> T measureTime(Supplier<T> codeToMeasure) {
    long t0 = System.currentTimeMillis();
    T r = codeToMeasure.get();
    long t1 = System.currentTimeMillis();
    System.out.println("Took " + (t1 - t0) + "ms");
    return r;
  }


  @SneakyThrows
  public static int sum(int a, int b) {
    ThreadUtils.sleep(ofSeconds(1));
    return a + b;
  }
  public static int multiply(int a, int b) {
    return a * b;
  }
}
