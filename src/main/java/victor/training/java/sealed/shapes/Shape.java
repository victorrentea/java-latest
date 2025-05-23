package victor.training.java.sealed.shapes;


public sealed interface Shape
    permits Circle, Square, Rectangle {
  double perimeter();
}

