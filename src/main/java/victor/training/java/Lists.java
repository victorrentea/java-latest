
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

void main() { // class-less main in java 21
  List<Integer> numbers = List.of(1, 2)
      .stream().filter(n->n%2==0)
      .toList();

  System.out.println(numbers);
  numbers.set(0, -1);
  System.out.println(numbers);
}
