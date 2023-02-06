//package victor.training.java.sealed;
//
//
//public class PerimeterVisitor implements ShapeVisitor {
//	private double totalPerimeter;
//
//	public void visit(Square square) {
//		totalPerimeter += 4 * square.edge();
//	}
//	public void visit(Circle circle) {
//		totalPerimeter += 2 * Math.PI * circle.radius();
//	}
//	public void visit(Rectangle rectangle) {
//		totalPerimeter += (rectangle.w() + rectangle.h()) * 2;
//	}
//	public double getTotalPerimeter() {
//		return totalPerimeter;
//	}
//}
