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
      // b. instanceOf
      // c. Visitor (anti)Pattern - 🤯 OMG
      // d. switch on sealed
    }
    System.out.println(totalPerimeter);
  }
}




