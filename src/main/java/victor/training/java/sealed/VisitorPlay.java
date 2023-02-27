package victor.training.java.sealed;


import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
            new Square(10), // 4 * E
            new Circle(5), // 2 * PI * R
            new Square(5),
            new Square(1),
            new Rectangle(4, 3));

    // TASK : compute the total perimeter
    double totalPerimeter = 0;

    // ### instanceof
//    for (Shape shape : shapes) {
//      if (shape instanceof Square sq) { // test de instanceof + declaratie de variabila 2-in-1
//        totalPerimeter += sq.edge() * 4;
//      } else if (shape instanceof Circle c) {
//        totalPerimeter += c.radius() * 2 * Math.PI;
//      } else {
//        throw new IllegalArgumentException("Unsupported type: " + shape.getClass().getSimpleName());
//      }
//      // PROBLEMA: daca apare maine si Dreptungiul => sare cu exceptie.
//        // pot mai bine (mai devreme => la compilare) sa vad lipsa?
//    }


    // ## OOP
    for (Shape shape : shapes) {
      totalPerimeter += shape.perimeter();
    }

    System.out.println("Total perimeter = " + totalPerimeter);


    // ## VISITOR
    // ## instanceOf
    // ## switch+sealed
  }
}


