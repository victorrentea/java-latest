package victor.training.java.virtualthread;

import jdk.jfr.Configuration;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VTPinningTest {

  private static Recording rec;

  public static void main(String[] args) {
    try {
      activate();
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void activate() throws IOException, ParseException {
    Configuration conf = Configuration.getConfiguration("default");
    rec = new Recording(conf);
    configureEvents(rec);
    // disable disk writes
    rec.setToDisk(true);
    rec.start();

    pinning();

    rec.stop();
    Path jfrPath = Path.of("recording-%s.jfr".formatted(System.currentTimeMillis()));
    rec.dump(jfrPath);
    System.out.println("END");

    read(jfrPath);
  }

  @SneakyThrows
  private static void read(Path jfrPath) {

    RecordingFile rec = new RecordingFile(jfrPath);

    while (rec.hasMoreEvents()) {
      RecordedEvent event = rec.readEvent();
      if ("jdk.VirtualThreadPinned".equals(event.getEventType().getName())) {
        Instant startTime = event.getStartTime();
        System.out.println("Pinned at: " + startTime + " event: " + event);
      }
    }

    rec.close();
  }

  private static void configureEvents(Recording rec) {

    for (EventType et : FlightRecorder.getFlightRecorder().getEventTypes()) {

      if (isEnabledForCrachDump(et)) {

        rec.enable(et.getName());

      }

    }

  }

  private static boolean isEnabledForCrachDump(EventType et) {
    System.out.println("Enabling event type: " + et.getName());
    return true;
  }

  public void dump(Path filename) throws IOException {

    rec.dump(filename);

  }

  @SneakyThrows
  @Test
  void experiment() {
    pinning();
  }

  @SneakyThrows
  public static Object pinning() {
    try (ExecutorService virtualThreads = Executors.newVirtualThreadPerTaskExecutor()) {
      return virtualThreads.submit(() -> method()).get();
    }
  }

  @SneakyThrows
  public static void method() {
    synchronized (VTPinningTest.class) {
      System.out.println("Start in " + Thread.currentThread().threadId());
      Thread.sleep(2000);
      System.out.println("End");
    }
  }

}
