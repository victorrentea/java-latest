package victor.training.java.sealed.model;

import victor.training.java.sealed.ShapeVisitor;

public final class Square implements Shape {
	private final int edge;

	public Square(int edge) {
		this.edge = edge;
	}

	@Override
	public void accept(ShapeVisitor visitor) {
		visitor.visit(this);
	}

	public int getEdge() {
		return edge;
	}

	@Override
	public String toString() {
		return "Square[" +
			   "getEdge=" + edge + ']';
	}

	public int calculateArea() {
		return edge * edge;
	}
}
