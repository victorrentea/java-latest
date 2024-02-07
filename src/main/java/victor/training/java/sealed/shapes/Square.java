package victor.training.java.sealed.shapes;

public record Square(int edge) implements Shape {
  @Override
  public double perimeter() {
    return 4 * edge;
  }
}
