import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

void main() { // class-less 'main' v21
  //region avoid Arrays.asList
  List<Integer> list = List.of(1, 2, 3);
//  list.set(0, 3); // can change👎 elements
  System.out.println(list);
  //endregion

  //region avoid .collect(toList());
  var odds = list.stream()
      .filter(i -> i % 2 == 1)
//      .collect(toList()); // returns mutable👎 ArrayList
//      .collect(toUnmodifiableList()); // returns mutable👎 ArrayList
      .toList();
//  odds.removeFirst();
  System.out.println(odds);
  //endregion
}