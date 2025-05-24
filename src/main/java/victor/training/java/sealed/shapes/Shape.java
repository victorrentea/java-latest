package victor.training.java.sealed.shapes;


public sealed interface Shape
    permits Circle, Square, Rectangle {
  double perimeter();

  default void banu() {
    System.out.println("bada da nu o face™️");
  }
}

