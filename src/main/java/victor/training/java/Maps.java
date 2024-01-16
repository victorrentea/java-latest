package victor.training.java;

import java.util.HashMap;
import java.util.Map;

public class Maps {
  private int[] state = new int[1000];
  public Map<String, Integer> returnsMap() {
    Map<String, Integer> map = Map.of(
        "one", 1,
        "two", 2);
    System.out.println(map);
    return map;
  }
}


