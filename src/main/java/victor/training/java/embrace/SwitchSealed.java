import java.util.List;

sealed interface Shape permits Circle, Square{
}
record Circle(int radius) implements Shape {

}
record Square(int edge) implements Shape {

}

void main(String[] args) {
  List<Shape> shapes = List.of(
      new Square(10), // 4 e
      new Circle(5), // 2 π r
      new Square(5));


  double totalPerimeter = 0; // TODO

  // a. polymorphism with behavior next to state (OOP)
  // b. instanceOf
  // c. Visitor (anti)Pattern - 🤯 OMG
  // d. switch on sealed

  System.out.println(totalPerimeter);
}




