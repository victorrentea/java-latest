package victor.training.java;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ToList {


  public static void main(String[] args) throws IOException {
    List<Integer> numbers = List.of(-4, -3, -2, 0, 1, 2, 3, -4, 5);

    // TODO find the negative numbers
    numbers.stream().filter(n -> n > 0).collect(Collectors.toList());
  }
}
