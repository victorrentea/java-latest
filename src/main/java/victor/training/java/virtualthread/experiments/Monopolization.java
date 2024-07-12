package victor.training.java.virtualthread.experiments;

import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;

@Slf4j
public class Monopolization {
  public static void main(String[] args) throws InterruptedException {
    try (var virtual = newVirtualThreadPerTaskExecutor()) {
      int cpus = Runtime.getRuntime().availableProcessors();
      for (int i = 0; i < cpus; i++) {
        virtual.submit(Monopolization::cpu);
      }
      Thread.sleep(100); // allow time to start all VTs
      virtual.submit(() -> {
        log.debug("Start other task");
      });
    }
  }

  public static void cpu() {
    log.debug("Start CPU");
    while (true) ;
  }
}
// Fix#1: -Djdk.virtualThreadScheduler.parallelism=20
// Fix#2: Thread.yield while CPU
