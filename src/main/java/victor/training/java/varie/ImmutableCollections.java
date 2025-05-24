import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

void main() { // stand-alone 'main' v21
  List<Integer> numbers = List.of(1, 2);
//  numbers.set(0, -1);
  System.out.println(numbers);

  final var var="alb";

  var odds = numbers.stream()
      .filter(i -> i % 2 == 1)
//      .collect(toList()); // ArrayList
//      .collect(Collectors.toUnmodifiableList());//💖 java11
//      .toList();//💖 java17
      .collect(ImmutableList.toImmutableList()); // Guava (google commons)
  //odds.removeFirst(); // v21
  System.out.println(odds);

  var s = """
      %s e cel mai tare
      din parcare""".formatted("Preshu");
//  var s = String.format("""
//      %s e cel mai tare
//      din parcare""", "Preshu");


  System.out.println(s);

}