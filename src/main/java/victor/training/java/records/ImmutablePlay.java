package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePlay {

  @Test
  void immutables() {
    List<Integer> numbers = new ArrayList<>(IntStream.range(1, 10).boxed().toList());
    Immutable obj = new Immutable("John", new Other("halo"), ImmutableList.copyOf(numbers));

    String original = obj.toString();
    System.out.println("Initial: " + obj);

    unknownFierceCode(obj);

    System.out.println("After:   " + obj);

    assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
  }

  private static void unknownFierceCode(Immutable obj) {
    // TODO what can go wrong here ?
    obj.getList().clear(); // acum crapa
//    System.out.println(obj.getList().isEmpty());
  }
}
