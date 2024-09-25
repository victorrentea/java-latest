import java.util.HashMap;
import java.util.Map;
import java.util.Set;

void main() { // java 21
  Map<String, Integer> map = new HashMap<>();
  map.put("one", 1);
  map.put("two", 2);
  System.out.println(Set.of(1, 2));
  System.out.println(map);
}

// new HashMap<>(){{ "idiom"
// Map.of
// Map.ofEntries