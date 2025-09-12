package victor.training.java.sealed.shapes;

public record Square(int edge)
    implements Shape {
  @Override
  public float calculatePerimeter() {
    return 4 * edge;
  }
}
