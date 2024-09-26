package victor.training.java.sealed.shapes;



import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10), // 4 * E
                new Circle(5), // 2 * PI * R
                new Square(5),
                new Square(1));

        // OOP
      double totalPerimeter = shapes.stream().mapToDouble(Shape::perimeter).sum();


      System.out.println(totalPerimeter);
    }
}


