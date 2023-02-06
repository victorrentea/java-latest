package victor.training.java.records;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePlay {

  @Test
  void immutables() {
    List<Integer> numbers = IntStream.range(1, 10).boxed().toList();
    Immutable obj = new Immutable("John", new Other("halo"), numbers);

    String original = obj.toString();
    System.out.println(obj);

    unknownFierceCode(obj);

    System.out.println(obj);

    assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
  }

  private static void unknownFierceCode(Immutable obj) {
    // TODO what can go wrong here ?
  }
}
