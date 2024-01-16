package victor.training.java.sealed.shapes;

import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Square(1));

    double totalPerimeter = 0; // TASK : compute

    // ## instanceOf
//    for (Shape shape : shapes) {
//      if (shape instanceof Square) {
//        totalPerimeter += 4 * ((Square) shape).edge();
//      } else if (shape instanceof Circle) {
//        totalPerimeter += 2 * Math.PI * ((Circle) shape).radius();
//      }
//    }

    // ## OOP (behavior next to state)
//    for (Shape shape : shapes) {
//      totalPerimeter += shape.perimeter();
//    }

    // ## VISITOR 😱
//      PerimeterVisitor perimeterVisitor = new PerimeterVisitor();
//    for (Shape shape : shapes) {
//      shape.accept(perimeterVisitor);
//    }
//    totalPerimeter = perimeterVisitor.getTotalPerimeter();

    // ## switch+sealed
    totalPerimeter = shapes.stream()
        .mapToDouble(shape -> switch (shape) {
          case Square(int edge) -> 4 * edge;
          case Circle circle -> 2 * Math.PI * circle.radius();
          case Rectangle(int edge1, int edge2) -> 2 * edge1 + 2 * edge2;
        })
        .sum();

    System.out.println(totalPerimeter);
  }
}


