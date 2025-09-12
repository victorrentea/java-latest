package victor.training.java.sealed.shapes;

public record Circle(int radius)
    implements Shape {
  @Override
  public float calculatePerimeter() {
    return 2 * (float)Math.PI * radius;
  }
}
