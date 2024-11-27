package victor.training.java.varie;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class Constants {
//  public static final Map<String, Integer> map =
//      new HashMap<>();
//  static {
//    map.put("one", 1);
//    map.put("two", 2);
//  }

  // hack: we create an anonymous subtype of HashMap and use an instance initializer to populate it
  // in a single expression = 💩
//  public static final Map<String, Integer> map = new HashMap<>() {{
//    put("one", 1);
//    put("two", 2);
//  }};

  //since java11:

  public static final Map<String, Integer> map = Map.of(
      "one", 1,
      "two", 2,
      "three", 3
  );
//  public static final Map<String, Integer> map =  Map.ofEntries(
//      entry("one", 1),
//      entry("two", 2),
//      entry("three", 3),
//      entry("four", 4),
//      entry("five", 5),
//      entry("six", 6),
//      entry("seven", 7),
//      entry("eight", 8),
//      entry("nine", 9),
//      entry("ten", 10),
//      entry("eleven", 11)
//  );
}
