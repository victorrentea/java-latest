package victor.training.java.sealed.shapes;



import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10), // 4 * E
                new Circle(5), // 2 * PI * R
                new Square(5),
                new Square(1));

        // #1 OOP sa vezi clasele ca Obiect, sa le permiti sa aiba behavior
//        for (Shape shape : shapes) {
//            totalPerimeter += shape.perimeter(); -> stream.sum()
//            list.add(e); -> stream.toList()
//        }
//        double totalPerimeter = shapes.stream().mapToDouble(Shape::perimeter).sum();


        // #2 DOP: sa vezi clasele doar ca Date si sa pui pe altcineva sa aplice formula corecta
        double totalPerimeter = 0;
        for (Shape shape : shapes) {
            if (shape instanceof Square square) {
                totalPerimeter += square.edge() * 4;
            }
            if (shape instanceof Circle circle) {
                totalPerimeter += 2 * Math.PI * circle.radius();
            }
        }


        // TASK : compute the total perimeter

        // ## OOP
        // ## VISITOR
        // ## instanceOf
        // ## switch+sealed
        System.out.println(totalPerimeter);
    }
}


