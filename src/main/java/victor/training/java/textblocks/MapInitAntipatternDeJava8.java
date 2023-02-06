package victor.training.java.textblocks;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class MapInitAntipatternDeJava8 {

  private static final Map<String, Integer> mapping = Map.ofEntries(
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1), // pt 10+ intrari, faci ofEntries
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("a",1),
          entry("b",2));

  // prea mult de scris
  public Map<String,Integer> muncitoreasca() {
    Map<String,Integer> map = new HashMap<>();
    map.put("a",1);
    map.put("b",2);
    return map;
  }
  // cripitic mem leak la pachet
  public Map<String,Integer> geek() {
    //varianta geek (stil lenes)
    return new HashMap<>() {// anonymous subclass de HashMap
      { // instance init block
      // ce e aici ???!!
      put("a",1);
      put("b",2);
    }};
  }

  public Map<String, Integer> java11() {
    return Map.of(
            "a",1,
            "b",2);
  }
}
