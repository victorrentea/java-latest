package victor.training.java.varie;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class SomeClass {
  public static final Map<String, Integer> map = new HashMap<>();

  static {
    map.put("one", 1);
    map.put("two", 2);
  }

  public static final Map<String, Integer> mapGeek = new HashMap<>()
  {{ // anonymous subclass of HashMap type with an instance initializer block
    put("one", 1);
    put("two", 2);
  }};

  public static final Map<String, Integer> mapJava11 = Map.of(
      "one", 1,
      "two", 2); // max 10 key-value pairs

  public static final Map<String, Integer> mapJava11Many = Map.ofEntries(
      Map.entry("one", 1),
      Map.entry("two", 2)
      // any number of key-value pairs
      );

  public static final ImmutableMap<String, Integer> guavaMap =
      ImmutableMap.<String, Integer>builder()
      .put("one", 1)
      .put("two", 2)
      .build();

  public static void main(String[] args) {
    mapJava11.put("three", 3);
    guavaMap.put("three", 3);
  }
}
