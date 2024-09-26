package victor.training.java.sealed.shapes;

public class PerimiterVisitor implements ShapeVisitor {
  private double perimeter = 0;

  @Override
  public void visit(Square square) {
    perimeter += 4 * square.edge();
  }

  @Override
  public void visit(Circle circle) {
    perimeter += 2 * Math.PI * circle.radius();
  }

  @Override
  public void visit(Rectangle rectangle) {
    perimeter += 2 * (rectangle.width() + rectangle.height());
  }

  public double getPerimeter() {
    return perimeter;
  }
}
