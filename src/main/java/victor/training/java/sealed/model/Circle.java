package victor.training.java.sealed.model;


import victor.training.java.sealed.ShapeVisitor;

public final class Circle implements Shape {
	private final int radius;

	public Circle(int radius) {
		this.radius = radius;
	}

	@Override
	public void accept(ShapeVisitor visitor) {
		visitor.visit(this);
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Circle[" +
			   "getRadius=" + radius + ']';
	}


}
