package victor.training.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToList {


  public static void main(String[] args) throws IOException {
    List<Integer> numbers = List.of(-4, -3, -2, 0, 1, 2, 3, -4, 5);

    // TODO find the negative numbers
    List<Integer> negatives = numbers.stream()
        .filter(n -> n < 0)
        .collect(Collectors.toList());
    System.out.println(negatives);
  }

  public static Map<String,Integer> mapInit() {
    Map<String,Integer> map = new HashMap<>();
    map.put("a",1);
    map.put("b",2);
    return map;
  }
}
