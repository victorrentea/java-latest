package victor.training.java;

import lombok.Value;

// TODO make this
//  POJO
//  immutable❤️
//  record
//  Lombok
record Interval(int start, int end) {
}

class Records {
  public void method(Interval interval) {
    System.out.println(interval.start());
  }
}