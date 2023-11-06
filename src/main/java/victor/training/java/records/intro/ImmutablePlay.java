package victor.training.java.records.intro;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePlay {

  @Test
  void immutables() {
    List<Integer> muta = Arrays.asList(1, 2, 3); // mutalbe list 🤢
    List<Integer> imm = List.of(1,2,3); // immutable❤️ list️, java11
    Set<Integer> set = Set.of(1, 2);// immutable❤️
    Map<String, Integer> map = Map.of("one", 1, "two", 2);// immutable❤️
    List<Integer> numbers = new ArrayList<>(IntStream.range(1, 10).boxed()
//        .collect(Collectors.toList()) // returns mutable list ArrayList 🤢
        .toList() // returns immutable❤️ list (java17)
    );
    Immutable obj = new Immutable(
        "John",
            new Other("halo"),
            numbers);

    String original = obj.toString();
    System.out.println("Initial: " + obj);

    unknownFierceCode(obj);

    System.out.println("After:   " + obj);

    assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
  }

  private static void unknownFierceCode(Immutable obj) {
//    obj.list().add(-7); // exceptie acum
//    System.out.println(obj.list());
  }
}
