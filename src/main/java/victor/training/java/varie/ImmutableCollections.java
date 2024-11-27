import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

void main() { // stand-alone 'main' v21
//  List<Integer> numbers = Arrays.asList(1, 2);
//  numbers.set(0, -1);
  List<Integer> numbers = List.of(1, 2);
//  numbers.set(0, -1);
  System.out.println(numbers);

  for (var e:numbers) {
    System.out.println(e);
  }

//  var x; // doesn't compile because type can't be inferred by the javac compiler
  var x = 1;
//  x="a";// fails
  var var = "var";
  Function<Integer,String> f=  Integer::toHexString;
//  var f=  Integer::toHexString; // target typing doesn't work

  var odds = numbers.stream()
      .filter(i -> i % 2 == 1)
//      .collect(toList()); // produces a mutable list (java8)
//      .collect(toUnmodifiableList());
//      .toList(); // produces an immutable list (java17)
      .collect(toImmutableList());

  // Careful: don't blindly replace .collect(toList()) with .toList() in existing code
  // because the former produces a mutable list, while the latter produces an immutable list
  // and the code might rely on the list being mutable.

//  odds.removeFirst();
//  odds.set(0, -1);
  System.out.println(odds);


}