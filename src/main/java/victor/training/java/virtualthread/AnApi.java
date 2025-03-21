package victor.training.java.virtualthread;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RestController
public class AnApi {
  private static final AtomicInteger counter = new AtomicInteger(0);
  private static final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(10);

  @GetMapping("call")
//  @RunOnVirtualThread // can be implemented
  public CompletableFuture<String> call() {





    CompletableFuture<String> cf = new CompletableFuture<>();
    scheduler.schedule(
        () -> cf.complete("data"+ counter.incrementAndGet()),
        5000,
        MILLISECONDS);
    return cf;
  }
}
