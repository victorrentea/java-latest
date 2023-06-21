package victor.training.java;

import java.util.List;


public class Sealed {
  interface Shape {}
  record Circle(int radius) implements Shape {}
  record Square(int edge) implements Shape {}

  public static void main(String[] args) {
    var shapes = List.of(new Square(10), new Circle(5));

    // TODO compute the total perimeter:
    double totalPerimeter = 0;
    // a) OOP
    // b) instanceOf
    // c) Visitor Pattern ☠️
    // d) switch on sealed types
    System.out.println(totalPerimeter);
  }

}
