package victor.training.java.sealed;


import victor.training.java.sealed.model.Circle;
import victor.training.java.sealed.model.Square;

public class PerimeterVisitor implements ShapeVisitor {

	private double totalPerimeter;
	
	@Override
	public void visit(Square square) {
		totalPerimeter += 4 * square.getEdge();
	}

	@Override
	public void visit(Circle circle) {
		totalPerimeter += 2 * Math.PI * circle.getRadius();
	}
	
	public double getTotalPerimeter() {
		return totalPerimeter;
	}

}
