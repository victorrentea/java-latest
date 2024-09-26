package victor.training.java.sealed.shapes;

public record Rectangle(int width, int height) implements Shape {
  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
