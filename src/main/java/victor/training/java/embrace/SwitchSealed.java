package victor.training.java.embrace;

import java.util.List;

sealed interface Shape permits Circle, Square { }
record Circle(int radius) implements Shape { }
record Square(int edge) implements Shape { }

public class SwitchSealed {
  void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 edge
        new Square(5),
        new Circle(5)); // 2 π radius

    double totalPerimeter = 0; // TODO
    for (Shape shape : shapes) {
      // a. polymorphism / OOP
//      totalPerimeter += shape.perimeter();
      // b. instanceOf
//      if (shape instanceof Square square) {
//        totalPerimeter += square.edge() * 4;
//      }
      // d. switch on sealed
      totalPerimeter += switch (shape) {
        case Circle(int radius) -> 2* Math.PI * radius;
        case Square(int edge) -> 4*edge;
      };
    }
    System.out.println(totalPerimeter);
  }
}




