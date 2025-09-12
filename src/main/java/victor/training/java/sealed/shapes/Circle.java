package victor.training.java.sealed.shapes;

public record Circle(int radius) implements Shape {
  @Override
  public void accept(ShapeVisitor shapeVisitor) {
    shapeVisitor.visit(this);
  }
}
