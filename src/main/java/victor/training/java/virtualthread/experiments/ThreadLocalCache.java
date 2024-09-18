package victor.training.java.virtualthread.experiments;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.Marshaller;
import org.springframework.web.client.RestTemplate;
import victor.training.java.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalCache {
  public static void main(String[] args) {
    try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
//    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) { // #2 VT->hijacks thread reuse
      for (int i = 0; i < 10000; i++) {
        executor.submit(() -> {
          someLibCode();
          Util.sleepMillis(10); // pretend some ops
          someLibCode();
        });
      }
    }
  }

  // --- inside a legacy library ---
  private static final ThreadLocal<String> threadCache =
      ThreadLocal.withInitial(ThreadLocalCache::expensiveInitOp);

  private static String expensiveInitOp() {
    return "library cached data " + "x".repeat(10000);
  }

  public static void someLibCode() { // old
//    String data = expensiveInitOp();
    String data = threadCache.get(); // #1 cache on thread
    System.out.println(data.substring(0, 20));
  }
}
