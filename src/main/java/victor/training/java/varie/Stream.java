package victor.training.java.varie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Stream {


  public static void main(String[] args) throws IOException {
    List<Integer> numbers = List.of(-4, -3, -2, 0, 1, 2, 3, -4, 5);

    System.out.println("After first negatives");
    numbers.stream().dropWhile(i -> i < 0).forEach(System.out::println);

    System.out.println("Until the first positive");
    numbers.stream().takeWhile(i -> i < 0).forEach(System.out::println);
  }
}
