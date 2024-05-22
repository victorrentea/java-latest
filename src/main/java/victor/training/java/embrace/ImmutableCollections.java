import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

void main() { // stand-alone 'main' v21
  List<Integer> list = Arrays.asList(1, 2, 3);
  list.set(0, 3);
  System.out.println(list);

  var odds = list.stream()
      .filter(i -> i % 2 == 1)
      .collect(toList());
  odds.removeFirst();
  System.out.println(odds);
}