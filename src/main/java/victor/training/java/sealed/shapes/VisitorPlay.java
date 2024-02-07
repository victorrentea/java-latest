package victor.training.java.sealed.shapes;

import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Square(1));

    // TASK : compute
    double totalPerimeter = 0.0;
    for (Shape shape : shapes) {
      totalPerimeter += getPerimeter(shape);
    }

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
    //    const {a,b} = f();
    // function() { return {a: 1, b: 2}; }

    System.out.println(totalPerimeter);
  }

  private static double getPerimeter(Shape shape) {
    return switch (shape) {
      case Square(int edge) -> 4 * edge;
      case Circle(int radius) -> 2 * Math.PI * radius;
      case Rectangle(int w, int h) -> 2 * (w + h);
    };
  }
}


