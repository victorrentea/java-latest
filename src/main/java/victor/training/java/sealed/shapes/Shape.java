package victor.training.java.sealed.shapes;

public interface Shape {
  void accept(ShapeVisitor shapeVisitor);
}
record Rectangle(int width, int height) implements Shape {
  @Override
  public void accept(ShapeVisitor shapeVisitor) {
    shapeVisitor.visit(this);
  }
}

