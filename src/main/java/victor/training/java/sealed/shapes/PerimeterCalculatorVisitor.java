package victor.training.java.sealed.shapes;

public class PerimeterCalculatorVisitor implements ShapeVisitor{
  private double perimeter;
  @Override
  public void visit(Square square) {
    perimeter += 4 * square.edge();
  }

  @Override
  public void visit(Circle circle) {
    perimeter += 2 * Math.PI * circle.radius();
  }

  public double getPerimeter() {
    return perimeter;
  }
}
