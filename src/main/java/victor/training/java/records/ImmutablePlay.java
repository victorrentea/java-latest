package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePlay {

  @Test
  void immutables() {
    List<Integer> numbers = new ArrayList<>(IntStream.range(1, 10).boxed().toList());
    Immutable obj = new Immutable("John",
            new Other("halo"),
            numbers);

    String original = obj.toString();
    System.out.println("Initial: " + obj);

    int price = computePrice(obj); // CQS

//    sendMessageKafka(obj);

    System.out.println("After:   " + obj);

    assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
  }

  private static int computePrice(Immutable obj) {
    // cod foarte mult si complex: are nevoie de 17 teste in total
//    obj.getList().add(-1); // dirty hack
//    System.out.println(obj.getList());
//    obj.getOther().setData("oups");
    System.out.println(obj.list());
    return 0;
  }
}
