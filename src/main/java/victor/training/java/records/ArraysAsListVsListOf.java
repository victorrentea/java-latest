package victor.training.java.records;

import java.util.Arrays;
import java.util.List;

public class ArraysAsListVsListOf {
  public static void main(String[] args) {


    List<Integer> list0 = Arrays.asList(1, 2, 3, 4);
    list0.set(0, -1); // NU CRAPA:>!!!!
//    list0.add(-1); // CRAPA!
    System.out.println("unu:" + list0);

    // asta e mai faina:
    List<Integer> list1 = List.of(1, 2, 3, 4);
    list1.set(0, -1); // CRAPA
//    list1.add(-1); // CRAPA
    System.out.println("doi:" + list1);
  }
}
