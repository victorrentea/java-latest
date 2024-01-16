package victor.training.java.sealed.shapes;


public record Circle(int radius) implements Shape {
//  @Override
//  public void accept(ShapeVisitor perimeterVisitor) {
//    perimeterVisitor.visit(this);
//  }
}


record Rectangle(int edge1, int edge2) implements Shape {}