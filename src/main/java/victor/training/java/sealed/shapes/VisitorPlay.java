package victor.training.java.sealed.shapes;


import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Square(1));

    double totalPerimeter = 0;

    //shapes.stream().mapToDouble(Shape::perimeter).sum(); // TASK : compute

//    for (Shape shape : shapes) {
//      if (shape instanceof Square square) {
//        totalPerimeter += 4 * square.edge();
//      } else if (shape instanceof Circle circle) {
//        totalPerimeter += 2 * Math.PI * circle.radius();
//      } else {
//        // mai bine o eroare sa-mi arat ca am uitat ceva
//        throw new IllegalStateException("Unknown shape: " + shape);
//      }
//    }
    // ## instanceOf
    // ## OOP (behavior next to state)
    // ## VISITOR 😱
    // ## switch+sealed
    for (Shape shape :shapes) {
      totalPerimeter += switch (shape) {
        case Square s -> s.edge() * 4;
        case Circle c -> c.radius() * 2 * Math.PI;
//        case Elipsa e when e.razaMare() < e.razaMare() -> 2 * Math.PI * Math.sqrt((e.razaMica() + e.razaMare()) / 2);
//        case Elipsa e when e.razaMare() >= e.razaMare() ->42;
        case Elipsa e -> fff(e);
        // in mod normal faceam asa:
//        default -> throw new IllegalStateException("Unknown shape: " + shape);
      };
    }


    System.out.println(totalPerimeter);
  }

  private static int fff(Elipsa e) {
    if (e.razaMare() < e.razaMica()) return 2 * Math.PI * Math.sqrt((e.razaMica() + e.razaMare()) / 2);
    return 42;
  }
}


