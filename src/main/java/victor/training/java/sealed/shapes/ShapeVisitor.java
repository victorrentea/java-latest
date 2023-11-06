package victor.training.java.sealed.shapes;


public interface ShapeVisitor {
	void visit(Square square);
	void visit(Circle circle);
//	void visit(Rectangle rectangle);
}
