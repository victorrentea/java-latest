import java.util.List;

interface Shape {
}

record Circle(int radius) implements Shape {
}

record Square(int edge) implements Shape {
}

void main(String[] args) {
  List<Shape> shapes = List.of(
      new Square(10), // 4 * E
      new Circle(5), // 2 * PI * R
      new Square(5));

  double totalPerimeter = 0; // TODO
  // a. instanceOf
  // b. OOP (behavior next to state)
  // c. Visitor (anti)Pattern - 🤯 OMG
  // d. switch on sealed
  System.out.println(totalPerimeter);
}




