package victor.training.java;

import java.util.HashMap;
import java.util.Map;

public class Maps {
  private int[] state = new int[1000];
  public void returnsMap() {
    Map<String, Integer> map = new HashMap<>();
    map.put("one", 1);
    map.put("two", 2);
    System.out.println(map);
  }
}


