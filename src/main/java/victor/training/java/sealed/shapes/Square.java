package victor.training.java.sealed.shapes;

public record Square(int edge) implements Shape {
  @Override
  public void accept(ShapeVisitor shapeVisitor) {
    shapeVisitor.visit(this);
  }
}
