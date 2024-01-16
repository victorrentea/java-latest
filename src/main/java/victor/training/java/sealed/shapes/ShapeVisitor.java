package victor.training.java.sealed.shapes;


public interface ShapeVisitor {
	void visit(Square square);
	void visit(Circle circle);
}

class PerimeterVisitor implements ShapeVisitor {
	double totalPerimeter = 0;
	@Override
	public void visit(Square square) {
		totalPerimeter += 4 * square.edge();
	}
	@Override
	public void visit(Circle circle) {
		totalPerimeter += 2 * Math.PI * circle.radius();
	}

	public double getTotalPerimeter() {
		return totalPerimeter;
	}
}