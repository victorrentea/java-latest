package victor.training.java.sealed;

public record Rectangle(int w, int h) implements Shape {
  @Override
  public double perimeter() {
    return 2*(w+h);
  }
  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
