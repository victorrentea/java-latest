package victor.training.java.records;

import lombok.Value;

@Value
public class LombokPeJava19 {
  String s;

  public static void main(String[] args) {
    System.out.println(new LombokPeJava19("a"));
    // it works! https://projectlombok.org/changelog de acu 3 zile :)
  }
}


