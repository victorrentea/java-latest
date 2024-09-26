package victor.training.java.sealed.shapes;

public class AreaVisitor implements ShapeVisitor {
   private double area;

   @Override
   public void visit(Circle circle) {
      area += Math.PI * circle.radius() * circle.radius();
   }

   @Override
   public void visit(Rectangle rectangle) {
      area += rectangle.width() * rectangle.height();
   }

   @Override
   public void visit(Square square) {
      area += square.edge() * square.edge();
   }

   public double getArea() {
      return area;
   }
}
