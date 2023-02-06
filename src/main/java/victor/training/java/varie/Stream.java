package victor.training.java.varie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Stream {


  public static void main(String[] args) throws IOException {
    List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    numbers.stream().dropWhile(i -> i < 0).forEach(System.out::println);
  }
}
