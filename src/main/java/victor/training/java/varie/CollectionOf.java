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
    System.out.println("Java8 style:" + list8);

    List<Integer> list11 = List.of(1, 2, 3, 4);
    System.out.println("Java11 style:" + list11);
    // TODO what's the difference ?
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
    return null; // TODO
  }

  private static final Map<String, Integer> mapMoreThan10Entries = null; // TODO
}
