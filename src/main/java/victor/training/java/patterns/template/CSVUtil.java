package victor.training.java.patterns.template;

import java.util.Objects;

public class CSVUtil {
  /** Override this method if you want to encrypt the exported file */ // "Hook" method
//  protected void encryptFile(File file) { /*NOOP*/ }

  // #2 reason for template method: when superclass provides some tools (methods)
  // that the sublclasses use to get their job done
  public static String escapeCell(Object cellValue) {
    if (cellValue instanceof String s) {
      if (!s.contains("\n")) return s;
      // abasda|12314|He said ""Ok""| "multiline
      // content"
      return "\"" + s.replace("\"", "\"\"") + "\"";
    } else {
      return Objects.toString(cellValue);
    }
  }
}
