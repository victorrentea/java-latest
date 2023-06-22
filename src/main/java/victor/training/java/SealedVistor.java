package victor.training.java;

import java.util.List;


public class SealedVistor {
  interface Shape {
    void accept(ShapeVisitor visitor);
  }
  record Circle(int radius) implements Shape {
    public void accept(ShapeVisitor visitor) {
      visitor.visit(this);
    }
  }
  record Square(int edge) implements Shape {
    public void accept(ShapeVisitor visitor) {
      visitor.visit(this);
    }
  }
  interface ShapeVisitor {
    void visit(Circle circle);
    void visit(Square square);
  }
  static class PerimeterVisitor implements ShapeVisitor {
    double totalPerimeter = 0;
    public void visit(Circle circle) {
      totalPerimeter += circle.radius * 2 * Math.PI;
    }
    public void visit(Square square) {
      totalPerimeter += square.edge * 4;
    }
  }

  public static void main(String[] args) {
    List<Shape> shapes = List.of(new Square(10), new Circle(5));

    // TODO compute the total perimeter:
    double totalPerimeter = 0;
    // a) OOP
    // b) instanceOf
    // c) Visitor Pattern ☠️
    PerimeterVisitor perimeterVisitor = new PerimeterVisitor();
    for (Shape shape : shapes) {
      shape.accept(perimeterVisitor);
    }
    totalPerimeter = perimeterVisitor.totalPerimeter;
    // d) switch on sealed types
    System.out.println(totalPerimeter);
  }

}
