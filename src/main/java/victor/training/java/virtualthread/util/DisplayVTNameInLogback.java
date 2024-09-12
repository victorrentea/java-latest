package victor.training.java.virtualthread.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayVTNameInLogback extends ClassicConverter {
  // example: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2
  private static Pattern virtualThreadNamePattern = Pattern.compile("VirtualThread\\[#(\\d+)[^]]+]/runnable@ForkJoinPool-(\\d+)-worker-(\\d+)");

  @Override
  public String convert(final ILoggingEvent e) {
    Thread thread = Thread.currentThread();
    if (thread.isVirtual()) {
      return friendlyVTName(thread.toString());
    }
    return String.valueOf(thread.toString());
  }

  public static String friendlyVTName(String threadToString) {
    Matcher matcher = virtualThreadNamePattern.matcher(threadToString);
    if (!matcher.matches()) {
      System.err.println(
          "Virtual thread name '" + threadToString + "' does not match the expected pattern: '" + virtualThreadNamePattern.pattern() +
          "'. Please update the pattern in " + DisplayVTNameInLogback.class.getName());
    }
    return "VT#" + matcher.group(1) + "/PT#" + matcher.group(3);
  }
}