
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

void main() { // class-less main in java 21
  List<Integer> numbers = Arrays.asList(1, 2);
  List<Integer> odds = numbers.stream()
      .filter(n -> n % 2 == 0)
      .map(n -> n * 2)
      .toList();
//      .collect(Collectors.toList());

  System.out.println(numbers);

  System.out.println(numbers);
}
