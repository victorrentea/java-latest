package victor.training.java.sealed;


public record Circle(int radius) implements Shape {
  @Override
  public double perimeter() {
    return 2 * radius * Math.PI;
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
