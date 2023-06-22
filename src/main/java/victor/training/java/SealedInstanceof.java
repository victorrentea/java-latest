package victor.training.java;

import java.util.List;


public class SealedInstanceof {
  interface Shape {}
  record Circle(int radius) implements Shape {}
  record Square(int edge) implements Shape {}

  public static void main(String[] args) {
    List<Shape> shapes = List.of(new Square(10), new Circle(5));

    // TODO compute the total perimeter:
    double totalPerimeter = 0;
    // a) OOP
    // b) instanceOf
    for (Shape shape : shapes) {
      if (shape instanceof Square square) {
        totalPerimeter += square.edge * 4;
      } else if (shape instanceof Circle circle) {
        totalPerimeter += circle.radius * 2 * Math.PI;
      }
    }
    // c) Visitor Pattern ☠️
    // d) switch on sealed types
    System.out.println(totalPerimeter);
  }

}
