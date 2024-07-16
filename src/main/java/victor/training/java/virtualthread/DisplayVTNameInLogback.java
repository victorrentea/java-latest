package victor.training.java.virtualthread;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayVTNameInLogback extends ClassicConverter {
  // example: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
  Pattern virtualThreadNamePattern = Pattern.compile("VirtualThread\\[#(\\d+)\\]/runnable@ForkJoinPool-(\\d+)-worker-(\\d+)");

  @Override
  public String convert(final ILoggingEvent e) {
    Thread thread = Thread.currentThread();
    if (thread.isVirtual()) {
      Matcher matcher = virtualThreadNamePattern.matcher(thread.toString());
      if (!matcher.matches()) {
        System.err.println(
            "Virtual thread name '" + thread + "' does not match the expected pattern: '" + virtualThreadNamePattern.pattern() +
            "'. Please update the pattern in " + DisplayVTNameInLogback.class.getName());
      }
      return "VT#" + matcher.group(1) + "/PT#" + matcher.group(3);
    }
    return String.valueOf(thread.toString());
  }
}