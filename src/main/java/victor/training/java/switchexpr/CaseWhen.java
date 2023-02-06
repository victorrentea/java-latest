package victor.training.java.switchexpr;

import victor.training.java.sealed.model.Circle;
import victor.training.java.sealed.model.Shape;
import victor.training.java.sealed.model.Square;

public class CaseWhen {

  public static void main(String[] args) {
    testSquare(new Square(12));
    testSquare(new Circle(12));
    testSquare(new Square(5));
    testSquare(null);
  }
  static void testSquare(Shape s) {
    if (s == null) return; // TODO replace with 'null' case
    switch (s) {
      case Square sq -> {
        if (sq.calculateArea() > 100) { // TODO replace with when (case refinement)
          System.out.println("Large Square");
          break;
        }
      }
//      case Shape sh -> { // TODO try this (overlapping case labels); mess with order
//        break ;
//      }

      default -> System.out.println("A shape, possibly a small triangle");
    }
  }
}
