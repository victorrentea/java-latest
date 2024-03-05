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
//        for (Shape shape : shapes) {
//            if (shape instanceof Square square) {
//                totalPerimeter += 4 * square.edge();
//            } else if (shape instanceof Circle) {
//                totalPerimeter += 2 * Math.PI * ((Circle) shape).radius();
//            } else {
//                throw new IllegalArgumentException("Unknown shape: " + shape);
//            }
//        }


    // ## OOP (behavior next to state)
//        for (Shape shape : shapes) {
//            totalPerimeter += shape.perimeter(); // polymorphic call
//        }


    // ## switch+sealed
//        for (Shape shape : shapes) {
//            totalPerimeter += switch (shape) {
//                case Square(int edge) -> 4 * edge; // const {a,b} = f();
//                case Circle(int radius) -> 2 * Math.PI * radius;
//            };
//        }

    totalPerimeter = shapes.stream()
        .mapToDouble(s -> switch (s) {
          case Square(int edge) -> 4 * edge;
          case Circle(int radius) -> 2 * Math.PI * radius;
        }).sum();

    System.out.println(totalPerimeter);
  }
}


