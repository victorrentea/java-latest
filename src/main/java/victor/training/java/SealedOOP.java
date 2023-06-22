package victor.training.java;

import java.util.List;


public class SealedOOP {
  interface Shape {
    double computePerimeter();
  }
  record Circle(int radius) implements Shape {
    @Override
    public double computePerimeter() {
      return radius * 2 * Math.PI;
    }
  }
  record Square(int edge) implements Shape {
    @Override
    public double computePerimeter() {
      return edge * 4;
    }
  }

  public static void main(String[] args) {
    List<Shape> shapes = List.of(new Square(10), new Circle(5));

    // TODO compute the total perimeter:
    double totalPerimeter = 0;
    // a) OOP
    for (Shape shape : shapes) {
      totalPerimeter += shape.computePerimeter();
    }
    // b) instanceOf
    // c) Visitor Pattern ☠️
    // d) switch on sealed types
    System.out.println(totalPerimeter);
  }

}
