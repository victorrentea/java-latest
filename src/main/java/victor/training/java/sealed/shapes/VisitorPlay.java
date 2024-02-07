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

    // ## OOP (behavior next to state) : polymorphism
//    for (Shape shape : shapes) {
//      totalPerimeter += shape.perimeter();
//    }

    // ## instanceOf
//    for (Shape shape : shapes) {
//      if (shape instanceof Square square) {
//        totalPerimeter += 4 * square.edge();
//      } else if (shape instanceof Circle circle) {
//        totalPerimeter += 2 * Math.PI * circle.radius();
//      } else {
//        throw new IllegalStateException("Unknown shape: " + shape);
//      }
//    }

    // ## VISITOR 😱
//    PerimeterCalculatorVisitor visitor = new PerimeterCalculatorVisitor();
//    for (Shape shape : shapes) {
//      shape.accept(visitor);
//    }
//    totalPerimeter = visitor.getPerimeter();


    // ## switch+sealed
    for (Shape shape : shapes) {
      totalPerimeter += switch(shape) {
        case Square square -> 4 * square.edge();
        case Circle circle -> 2 * Math.PI * circle.radius();
      };
    }

    System.out.println(totalPerimeter);
  }
}


