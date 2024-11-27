package victor.training.java.varie;

import java.util.HashMap;
import java.util.Map;

public class Maps {
  public static void main() {
//  Map<String, Integer> map = new HashMap<>();
//  map.put("one", 1);
//  map.put("two", 2);
//  ImmutableCollections s;
  Map<String, Integer> map = Map.of(
      "one", 1,
      "two", 2
  );
//  map.put("two", 3);
  System.out.println(map);
}

// new HashMap<>(){{ "idiom"
// Map.of
// Map.ofEntries
}