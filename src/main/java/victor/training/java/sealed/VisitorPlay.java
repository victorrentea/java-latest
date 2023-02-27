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


    // ## OOP : keep behavior next to state + javac crapa daca NU ai implem metoda din interfata.
//    for (Shape shape : shapes) {
//      totalPerimeter += shape.perimeter();
//    }


    // ## VISITOR design pattern: sa implem logica specifica unor tipuri concrete dar !!
    // FARA SA ADAUGI LOGICA IN CLASELE RESPECTIVE => Anti-pattern in java 17
//    PerimeterVisitor perimeterVisitor = new PerimeterVisitor();
//    for (Shape shape : shapes) {
//      shape.accept(perimeterVisitor);
//    }
//    totalPerimeter = perimeterVisitor.getTotal();


    // ## sealed interface + switch(shape) - java 21
    for (Shape shape : shapes) {
      totalPerimeter += switch (shape) {
        case Circle(int r) -> 2 * r * Math.PI;
        case Rectangle(int w, int h) /*when w>10*/ -> 2 * (w + h);
        case Square(int edge) -> 4 * edge;
//        case Rectangle rectangle -> 10;
      };
    }

    System.out.println("Total perimeter = " + totalPerimeter);

  }

  public void method() {
//    Rectangle(int w,int h) = f(); // not in scope
  }
}


