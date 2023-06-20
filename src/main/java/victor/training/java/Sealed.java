package victor.training.java;

import java.util.List;

interface Shape {
}

record Circle(int radius) implements Shape {
}

record Square(int edge) implements Shape {
}

public class Sealed {
  public static void main(String[] args) {
    List<Shape> shapes = List.of(new Square(10), new Circle(5), new Square(1));

    // TODO compute the total perimeter using (enable copilot😏):

    // a) instanceOf
    // b) Visitor Pattern ☠️
    // c) switch on sealed types
  }

}
