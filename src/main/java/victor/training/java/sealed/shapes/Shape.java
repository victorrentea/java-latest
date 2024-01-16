package victor.training.java.sealed.shapes;


public sealed interface Shape permits Square, Circle, Rectangle {
//  void accept(ShapeVisitor perimeterVisitor);
}

