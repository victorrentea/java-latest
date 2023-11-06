package victor.training.java.sealed.shapes;



import java.awt.*;
import java.util.List;

public class VisitorPlay {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Square(10), // 4 * E
                new Circle(5), // 2 * PI * R
                new Square(5),
                new Rectangle(1,2),
                new Square(1));

        // #1 OOP sa vezi clasele ca Obiect, sa le permiti sa aiba behavior
//        for (Shape shape : shapes) {
//            totalPerimeter += shape.perimeter(); -> stream.sum()
//            list.add(e); -> stream.toList()
//        }
//        double totalPerimeter = shapes.stream().mapToDouble(Shape::perimeter).sum();


        // #2 DOP: sa vezi clasele doar ca Date si sa pui pe altcineva sa aplice formula corecta
//        double totalPerimeter = 0;
//        for (Shape shape : shapes) {
//            if (shape instanceof Square square) {
//                totalPerimeter += square.edge() * 4;
////                continue;  break; // nu sunt FP-friendly
//            } else if (shape instanceof Circle circle) {
//                totalPerimeter += 2 * Math.PI * circle.radius();
//            } else {
//                throw new IllegalArgumentException("N-o stiu p-asta: " + shape);
//            }
//            // as vrea sa arunc exceptie cand in lista e un nou tip de forma pe care eu nu-l suport
//            // in Java <=11
//        }

        // #3 Visitor Pattern (java <=11 )
        // 1) sa fac compilatorul sa crape daca uit sa implem logica pe un tip <- nu mai e folosit Visitor din Java >= 17
        // 2) sa mut logica asta intr-o clasa dedicata.
//        PerimeterVisitor perimeterVisitor = new PerimeterVisitor() ;
//        for (Shape shape : shapes) {
//            shape.accept(perimeterVisitor);
//        }

        // #4 sealed classes
        double totalPerimeter = 0;
        for (Shape shape : shapes) {
            totalPerimeter += switch (shape) { // PR comment: foloseste switch ca expresie nu ca statement
                case Square(var edge) -> edge * 4;
                case Circle(var radius) -> radius * 2 * Math.PI;
                case Rectangle(int w, int h) -> 2 * (w + h);
            };
        }
        // destructurare inseamna sa extragi componentele unei structuri intr-un foc
        // let {a, b} = f(); in f() {return {a:1, b:2};}

        // TASK : compute the total perimeter

        // ## OOP
        // ## VISITOR
        // ## instanceOf
        // ## switch+sealed
        System.out.println(totalPerimeter);
    }
}


