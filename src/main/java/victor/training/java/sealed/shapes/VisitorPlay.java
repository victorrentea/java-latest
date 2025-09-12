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

        // OOP: behavior kept next to state
        for (Shape shape : shapes) {
            totalPerimeter += shape.calculatePerimeter();
        }

        //








        // ## instanceOf
        // ## OOP (behavior next to state)
        // ## VISITOR 😱
        // ## switch+sealed

        System.out.println(totalPerimeter);
    }
}


