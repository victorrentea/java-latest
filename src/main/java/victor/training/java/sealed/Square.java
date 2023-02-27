package victor.training.java.sealed;

public record Square(int edge) implements Shape {
  @Override
  public double perimeter() {
    return 4  * edge;
  }
  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
