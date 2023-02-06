package victor.training.java.varie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class CollectionOf {
  public static void main(String[] args) {
    ListOf_vs_ArraysAsList();

    System.out.println(classicButTooMuchToWrite());
    System.out.println(geekButPotentialMemLeak());
    System.out.println(java11());
  }

  private static void ListOf_vs_ArraysAsList() {
    List<Integer> list8 = Arrays.asList(1, 2, 3, 4);
    list8.set(0, -1); //DOES NOT THROW!! :((
    //    list8.add(-1); // THROWS :)
    System.out.println("unu:" + list8);

    List<Integer> list11 = List.of(1, 2, 3, 4);
    //    list11.set(0, -1); // THROWS :) ❤️
    //    list11.add(-1); // THROWS :)
    System.out.println("doi:" + list11);
  }



  public static Map<String,Integer> classicButTooMuchToWrite() {
    Map<String,Integer> map = new HashMap<>();
    map.put("a",1);
    map.put("b",2);
    return map;
  }

  public static Map<String,Integer> geekButPotentialMemLeak() {
    return new HashMap<>() {{
      // instance init block
      put("a",1);
      put("b",2);
    }};
  }

  public static Map<String, Integer> java11() { // ❤️
    return Map.of(
            "a",1,
            "b",2);
  }

  private static final Map<String, Integer> mapMoreThan10Entries = Map.ofEntries(
          entry("k1",1),
          entry("k2",1),
          entry("k3",1),
          entry("k4",1),
          entry("k5",1),
          entry("k6",1),
          entry("k7",1),
          entry("k8",1),
          entry("k9",1),
          entry("k10",1), // for > 10+ entries, use ofEntries
          entry("k11",1));
}
