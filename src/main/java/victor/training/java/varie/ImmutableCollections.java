import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

void main() { // stand-alone 'main' v21
//  List<Integer> numbers = Arrays.asList(1, 2, 3); // java 8
  List<Integer> numbers = List.of(1, 2, 3); // java 11 cannot change the list
//  numbers.add(88);
//  numbers.set(0, 77);
  System.out.println(numbers);

  var odds = numbers.stream()
      .filter(i -> i % 2 == 1)
//      .collect(toList()); // 8
      .toList(); // 17💖
//  odds.remove(0); // it works with coolect(toList())
//  odds.remove(0); // it fails on Stream#toList

//  odds.removeFirst(); // v21
  System.out.println(odds);
}