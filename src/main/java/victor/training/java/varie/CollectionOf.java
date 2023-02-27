package victor.training.java.varie;

import victor.training.java.records.Other;

import java.util.*;

import static java.util.Map.entry;

public class CollectionOf {
  public static void main(String[] args) {
    ListOf_vs_ArraysAsList();
//    Other a;
//    System.out.println(classicButTooMuchToWrite());
//    System.out.println(geekButPotentialMemLeak());
//    System.out.println(java11());
  }

  private static void ListOf_vs_ArraysAsList() {
    List<Integer> list8 = Arrays.asList(1, 2, 3, 4);
//    list8.add(99);
    list8.set(0, -99);
    System.out.println("Java8 style:" + list8);

    List<Integer> list11 = List.of(1, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4);
//    list11.add(99);
    list11.set(0, -99);
    System.out.println("Java11 style:" + list11);
    // TODO what's the difference ?
  }

  private static final Map<String, Integer> MAPA_CONSTANTA = new HashMap<>() { // anonymous subclass extends HashMap
    { // instance init block - ruleaza odata cu ctor (la new)
      put("a",1); // imi chem singur metoda de instanta
      put("b",2);
  }};

//  static { // ce asta ? - static init block pentru clasa; ruleaza cand se incarca .class (prima data cand e referita clasa asta din alta parte) adica inaintea ctor
//    MAPA_CONSTANTA.put("a",1);
//    MAPA_CONSTANTA.put("b",2);
//  }


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
    // potential memory leak pentru ca instanta de subclasa anonima
    // de HashMap tine ref la toate campurile instantei curente
  }

  public static Map<String, Integer> java11() { // ❤️
    return Map.of(
            "a",1,
            "b",2);
  }

  private static final Map<String, Integer> mapMoreThan10Entries = Map.ofEntries(
          entry("a",1),
          entry("b",1),
          entry("c",1),
          entry("d",1),
          entry("e",1),
          entry("f",1),
          entry("g",1),
          entry("h",1),
          entry("j",1),
          entry("i",1),
          entry("k",1)
  );
}
