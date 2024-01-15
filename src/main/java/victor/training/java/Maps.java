
import java.util.HashMap;
import java.util.Map;

void main() {
  Map<String, Integer> map = new HashMap<>();
  map.put("one", 1);
  map.put("two", 2);
  System.out.println(map);
}

// new HashMap<>(){{ "idiom"
// Map.of
// Map.ofEntries