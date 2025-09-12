package victor.training.java.sealed.shapes;

public class PerimeterVisitor implements ShapeVisitor{
  int totalPerimeter = 0;
  @Override
  public void visit(Square square) {
    totalPerimeter += 4 * square.edge();
  }
  @Override
  public void visit(Circle circle) {
    totalPerimeter += (int) (2 * Math.PI * circle.radius());
  }

  @Override
  public void visit(Rectangle circle) {
    totalPerimeter += 2 * (circle.height() + circle.width());
  }

  public int getTotalPerimeter() {
    return totalPerimeter;
  }
}
