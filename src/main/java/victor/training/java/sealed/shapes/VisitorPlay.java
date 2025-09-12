package victor.training.java.sealed.shapes;


import java.util.List;

public class VisitorPlay {

  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Rectangle(2,3),
        new Square(1));
    double totalPerimeter = 0; // TASK : compute


    // OOP: behavior kept next to state
//        for (Shape shape : shapes) {
//            totalPerimeter += shape.calculatePerimeter();
//        }

    // instanceof: tine logica aici
//    for (Shape shape : shapes) {
//      if (shape instanceof Square s) { // si declari var Square s;
//        totalPerimeter += 4 * s.edge();
//      } else if (shape instanceof Circle c) {
//        totalPerimeter += 2 * Math.PI * c.radius();
//      } else {
//        throw new IllegalStateException("Unknown shape: " + shape);
//      }
//    }

    // ## VISITOR 😱 : vrei sa te asiguri la compilare ca nu ratezi vreun subtip
    // + te protejeaza de a uita un subtip din ierarhie
    // + poti adauga operatii noi per element fara sa atingi clasa elementului
    PerimeterVisitor perimeterVisitor = new PerimeterVisitor();
    for (Shape shape : shapes) {
      shape.accept(perimeterVisitor);
    }
    totalPerimeter = perimeterVisitor.getTotalPerimeter();

    // ## switch+sealed; in Java 21 Visitor e antipattern


    System.out.println(totalPerimeter);
  }
}


