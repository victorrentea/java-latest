package victor.training.java.sealed;


import victor.training.java.sealed.model.Circle;
import victor.training.java.sealed.model.Shape;
import victor.training.java.sealed.model.Square;

import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10),
                new Circle(5),
                new Square(5));

        double totalPerimeter = 0;
        System.out.println("Total perimeter: " + totalPerimeter);

//        double totalArea = 0;// TODO
//        System.out.println("Total area: " + totalArea);
    }
}



