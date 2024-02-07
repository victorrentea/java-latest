package victor.training.java.sealed.shapes;


public record Circle(int radius) implements Shape {
//  @Override
//  public void accept(ShapeVisitor visitor) {
//    visitor.visit(this);
//  }

//  @Override
//  public double perimeter() {
//    return 2 * Math.PI * radius;
//  }
}
