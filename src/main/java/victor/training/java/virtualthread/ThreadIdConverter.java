package victor.training.java.virtualthread;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadIdConverter extends ClassicConverter {
  @Override
  public String convert(final ILoggingEvent e) {
    return String.valueOf(Thread.currentThread().toString());
  }
}