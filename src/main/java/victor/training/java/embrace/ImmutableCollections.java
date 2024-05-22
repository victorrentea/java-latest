import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

void main() { // stand-alone 'main' v21
  //region avoid Arrays.asList
  List<Integer> list = Arrays.asList(1, 2, 3);
  list.set(0, 3); // can change👎 elements
  System.out.println(list);
  //endregion

  //region avoid .collect(toList());
  var odds = list.stream()
      .filter(i -> i % 2 == 1)
      .collect(toList()); // returns mutable👎 ArrayList
  odds.removeFirst();
  System.out.println(odds);
  //endregion
}