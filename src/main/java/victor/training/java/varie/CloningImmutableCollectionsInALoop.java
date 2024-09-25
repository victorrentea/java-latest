package victor.training.java.varie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CloningImmutableCollectionsInALoop {

  public static void main(String[] args) {
//    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> numbers = IntStream.range(1, 100_000).boxed().toList();

    long t0 = System.currentTimeMillis();
    List<Integer> immResults = List.of();
    List<Integer> mutableResults = new ArrayList<>();
    for (int n: numbers) {
      immResults = addToAnImmutableList(immResults, n*n);
//      mutableResults.add(n*n);
    }
    System.out.println(immResults.size());
    long t1 = System.currentTimeMillis();
    System.out.println("Time: " + (t1 - t0) + " ms");
  }

  public static List<Integer> addToAnImmutableList(List<Integer> immutable, int value) {
    List<Integer> copy = new ArrayList<>(immutable);
    copy.add(value);
//    return List.copyOf(copy);
//    return Collections.unmodifiableList(copy);
    return copy;
  }
}
