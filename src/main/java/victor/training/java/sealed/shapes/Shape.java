package victor.training.java.sealed.shapes;


public interface Shape {
    void accept(ShapeVisitor visitor);
}

