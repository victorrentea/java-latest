package victor.training.java;

import java.util.List;


public class Sealed {
  sealed interface Shape permits Circle, Rectangle, Square {}
  record Circle(int radius) implements Shape {}
  record Square(int edge) implements Shape {}
  record Rectangle(int w, int h) implements Shape {}

  public static void main(String[] args) {
    List<Shape> shapes = List.of(new Square(10), new Circle(5));

    // TODO compute the total perimeter:
    double totalPerimeter = 0;
    // a) OOP
    // b) instanceOf
    for (Shape shape : shapes) {
      totalPerimeter += switch (shape) {
        case Square square -> square.edge * 4;
        case Circle circle -> circle.radius * 2 * Math.PI;
        case Rectangle rectangle -> 2* rectangle.w + 2*rectangle.h;
      };
    }
    // c) Visitor Pattern ☠️
    // d) switch on sealed types
    System.out.println(totalPerimeter);
  }

}
