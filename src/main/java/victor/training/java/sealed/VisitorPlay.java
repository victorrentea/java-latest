package victor.training.java.sealed;


import victor.training.java.sealed.Shape.Circle;
import victor.training.java.sealed.Shape.Rectangle;
import victor.training.java.sealed.Shape.Square;

import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10), // 4 * E
                new Circle(5), // 2 * PI * R
                new Square(5),
                new Square(1));

        // TASK : compute the total perimeter

        // ## OOP
        // ## VISITOR
        // ## instanceOf
        // ## switch+sealed
    }
}


