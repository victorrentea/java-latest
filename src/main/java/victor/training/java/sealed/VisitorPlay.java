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
                new Square(5));

        // ## OOP cu apel polimorfic de getPerimeter (ani de liceu)
//        double totalPerimeter = shapes.stream().mapToDouble(Shape::getPerimeter).sum();

        // ## instanceof (tii toata logica tuturor gramada aici)
//        double totalPerimeter = 0;
//        for (Shape shape : shapes) {
//            if (shape instanceof Square sq) { //instanceof cu declaratie de variabila
//                totalPerimeter += sq.getEdge() * 4;
//            } else if (shape instanceof Circle c) { //instanceof cu declaratie de variabila
//                totalPerimeter += c.getRadius() * 2 * Math.PI;
//            } else { // aka default din switch
//                throw new IllegalArgumentException("Ce hal de forma e asta: " + shape);
//            }
//        }


        // ## Visitor pattern
//        PerimeterVisitor visitor = new PerimeterVisitor();
//        for (Shape shape : shapes) {
//            shape.accept(visitor);
//        }
//        double totalPerimeter = visitor.getTotalPerimeter();

        // ## sealed classes + switch pe ierarhie

        double totalPerimeter = 0;
        for (Shape shape : shapes) {
            totalPerimeter += switch (shape) {
                case Circle c -> c.radius() * Math.PI * 2;
                case Rectangle r -> (r.h() + r.w()) * 2;
                case Square s -> s.edge() * 4;
             };
        }
        System.out.println("Total perimeter: " + totalPerimeter);


//        double totalArea = 0;// TODO
//        System.out.println("Total area: " + totalArea);
    }
}


