package victor.training.java.sealed;

import victor.training.java.sealed.Problema.Shape.Circle;
import victor.training.java.sealed.Problema.Shape.Rectangle;
import victor.training.java.sealed.Problema.Shape.Square;

import java.util.List;

class Problema {
  enum Material {CATIFEA, MATASE, BUMBAC} // don't touch this

  sealed interface Shape {
    Material material();
    record Square(int edge,Material material) implements Shape {}
    record Circle(int radius,Material material) implements Shape {}
    record Rectangle(int w, int h,Material material) implements Shape {}
  }

  //Preturi = catifea=60, matase=250, bumbac=30 lei/mp


  public static void main(String[] args) {
    List<Shape> shapes = List.of(
            new Shape.Rectangle(2,3,Material.CATIFEA),
            new Shape.Square(2, Material.MATASE));

    double totalPrice = shapes.stream().mapToDouble(s -> getSurface(s) * getPricePerSurface(s.material())).sum();

    System.out.println(totalPrice);
  }

  public static double getSurface(Shape shape) {
    return switch (shape) {
      // java 21 sep 2023
      case Square(int edge, Material material) -> edge * edge;
      case Circle(int radius, Material material) -> Math.PI * radius * radius;
      case Rectangle(int w, int h, Material material) -> w * h;
    };
  }
  public static int getPricePerSurface(Material material) {
    return switch (material) {
      // java 17
      case CATIFEA -> 60;
      case MATASE -> 250;
      case BUMBAC -> 30;
    };
  }
}


// TODO PieceOfMaterial