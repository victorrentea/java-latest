//package victor.training.java.sealed;
//
//public class PerimeterVisitor implements ShapeVisitor{
//  private double total = 0;
//  @Override
//  public void visit(Square square) {
//    total += square.edge()*4;
//  }
//
//  @Override
//  public void visit(Circle circle) {
//    total += circle.radius() * 2 * Math.PI;
//  }
//
//  @Override
//  public void visit(Rectangle rectangle) {
//    total += 2 * (rectangle.w() + rectangle.h());
//  }
//
//  @Override
//  public void visit(Elipsis elipsis) {
////    total += todo
//  }
//
//  public double getTotal() {
//    return total;
//  }
//}
