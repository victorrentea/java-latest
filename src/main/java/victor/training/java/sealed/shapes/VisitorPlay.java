package victor.training.java.sealed.shapes;


import java.util.List;

public class VisitorPlay {
  public static void main(String[] args) {
    List<Shape> shapes = List.of(
        new Square(10), // 4 * E
        new Circle(5), // 2 * PI * R
        new Square(5),
        new Rectangle(2,3),
        new Square(1));


    // data-oriented-programming (DOP): +totul local aici +nu pot modifica clasele
    double totalPerimeter = shapes.stream()
        .mapToDouble(shape -> switch (shape) {
          case Circle(var radius) -> 2 * Math.PI * radius;
          case Square(var edge) -> 4 * edge; // destructurare de record
          case Rectangle r -> 2 * (r.getHeight()+r.getWidth());

//          case R1 r1 -> 0;
//          case R2 r2 -> 0;
//          case R3 r3 -> 0; // face switch sa nu compileze
//          default -> throw new IllegalStateException("Riscant: crapa in prod:L Unexpected value: " + shape); // epic fail
        })
        .sum();

    // OOP polymorphic: +encapsulation +modular +imprastie logic
    var totalPerimeter2 = shapes.stream().mapToDouble(Shape::perimeter).sum();

    System.out.println(totalPerimeter);
  }
}


