import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

void main() { // java 21 ftw
  List<Integer> numbers = asList(1, 2);

  numbers = List.copyOf(numbers);
//
//  numbers.set(0, 3);
//  System.out.println(numbers);

  // select only the even numbers
  List<Integer> evenNumbers = numbers.stream()
      .filter(n -> n % 2 == 0)
//      .collect(Collectors.toList());
      .toList();
  evenNumbers.clear();
  System.out.println(evenNumbers);
}


// List.of
// List.stream().toList()