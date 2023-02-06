package victor.training.java.sealed;


import victor.training.java.sealed.model.Circle;
import victor.training.java.sealed.model.Rectangle;
import victor.training.java.sealed.model.Square;

public interface ShapeVisitor {
	void visit(Square square);
	void visit(Circle circle);
	void visit(Rectangle rectangle);
}
