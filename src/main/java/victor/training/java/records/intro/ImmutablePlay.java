package victor.training.java.records.intro;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePlay {

  @Test
  void immutables() {
    ImmutableList<Integer> numbers =
        IntStream.range(1, 10).boxed()
            .collect(ImmutableList.toImmutableList());
    Immutable obj = new Immutable("John",
            1,2,
            new Other("halo"),
            numbers);

    String original = obj.toString();
    System.out.println("Initial: " + obj);

//    obj = unknownFierceCode(obj); // Clean Code nu-i sa reciclezi variab pt intelesuri diferit
    Immutable movedObj = unknownFierceCode(obj); // Clean Code nu-i sa reciclezi variab pt intelesuri diferit

    System.out.println("After (mutat):   " + movedObj);

    assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
  }

  private static Immutable unknownFierceCode(Immutable obj) {
//    obj.list().add(1);
//    System.out.println(obj.list());
//    return obj.toBuilder()
//        .lat(obj.lat()+1)
//        .lon(obj.lon()+1)
//        .build(); // reimpachetez immutabil

//    return obj
//          .withLat(obj.lat()+1)
//          .withLon(obj.lon()+1);

    return obj.translate(1, 1);
  }
}
