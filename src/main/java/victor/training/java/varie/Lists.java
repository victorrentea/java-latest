import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

void main() { // stand-alone 'main' >= v21
  List<Integer> numbers = Arrays.asList(1, 2);
  numbers.set(0, -1);
  System.out.println(numbers);

  var odds = numbers.stream()
      .filter(i -> i % 2 == 1)
      .collect(toList());
  odds.removeFirst(); // >= v21
  System.out.println(odds);
}

//