package victor.training.java.varie;

import java.util.*;

public class SequencedCollections {
  public static void main(String[] args) {
    var list = new ArrayList<>(List.of(1, 2, 3));
    var reversed = list.reversed();
    System.out.println(list.getFirst() + list.removeLast());
    list.addFirst(4);
    System.out.println(reversed.getLast());

    var map = new LinkedHashMap<>(Map.of("one", 1, "two", 2));
    map.putFirst("zero", 0);
    map.lastEntry().setValue(3);


  }
}
