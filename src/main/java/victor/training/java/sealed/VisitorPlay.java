package victor.training.java.sealed;


import org.checkerframework.checker.units.qual.C;
import victor.training.java.sealed.model.Circle;
import victor.training.java.sealed.model.Rectangle;
import victor.training.java.sealed.model.Shape;
import victor.training.java.sealed.model.Square;

import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10), // 4 * E
                new Circle(5), // 2 * PI * R
                new Square(5),
                new Square(1));

        // ## OOP cu apel polimorfic de getPerimeter (ani de liceu)
//        double totalPerimeter = shapes.stream().mapToDouble(Shape::getPerimeter).sum();

        // ## instanceof (tii toata logica tuturor gramada aici)
        double totalPerimeter2 = 0;
        for (Shape shape : shapes) {
            if (shape instanceof Square(int edge)) { //instanceof cu declaratie de variabila
                totalPerimeter2 += edge * 4;
            } else if (shape instanceof Circle(int radius)) { //instanceof cu declaratie de variabila
                totalPerimeter2 += radius * 2 * Math.PI;
            } else { // aka default din switch
                throw new IllegalArgumentException("Ce hal de forma e asta: " + shape);
            }
        }


        // ## Visitor pattern
//        PerimeterVisitor visitor = new PerimeterVisitor();
//        for (Shape shape : shapes) {
//            shape.accept(visitor);
//        }
//        double totalPerimeter = visitor.getTotalPerimeter();

        // ## sealed classes + switch pe ierarhie
// patratele cu edge < 2 sa fie ignorate
        double totalPerimeter = 0;
        for (Shape shape : shapes) {
            totalPerimeter += switch (shape) {
                case Circle(int radius) -> radius * Math.PI * 2;
                case Rectangle(int w,int h) -> (w + h) * 2;
                case Square(int edge) -> {
                    if (edge < 2) yield 0;
                    yield edge * 4; // returneaza din switchul curent
                    // a nu se confunda cu return care iese din metoda !!!
                }
             };
        }
        System.out.println("Total perimeter (91): " + totalPerimeter);


//        double totalArea = 0;// TODO
//        System.out.println("Total area: " + totalArea);
    }
}


