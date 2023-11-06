package victor.training.java.sealed.shapes;

public class PerimeterVisitor implements ShapeVisitor {
  private double totalPerimeter;
  @Override
  public void visit(Square square) {
    totalPerimeter += square.edge() * 4;
  }

  @Override
  public void visit(Circle circle) {
    totalPerimeter += circle.radius() * 2 * Math.PI;
  }

  public double getTotalPerimeter() {
    return totalPerimeter;
  }
}
