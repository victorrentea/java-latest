package victor.training.java.sealed.shapes;


import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Rectangle(2, 3),
        new Square(1));

    // OOP💖
//  double totalPerimeter = shapes.stream()
//    .mapToDouble(Shape::perimeter).sum();

//    double totalPerimeter = 0;
//    for (Shape shape : shapes) {
//      if (shape instanceof Square square) {
//        totalPerimeter += 4 * square.edge();
//      } else if (shape instanceof Circle circle) {
//        totalPerimeter += 2 * Math.PI * circle.radius();
//      } else { // to guard us against future types of shapes
//        throw new IllegalArgumentException("Unknown shape: " + shape);
//      }
//    }

    // VIsitor design pattern, became 99% anti pattern in java 21
    AreaVisitor visitor = new AreaVisitor();
    for (Shape shape : shapes) {
      shape.accept(visitor);
    }
    double totalPerimeter = visitor.getArea();



    System.out.println(totalPerimeter);
  }

}


