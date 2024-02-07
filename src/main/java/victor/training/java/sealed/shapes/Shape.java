package victor.training.java.sealed.shapes;


public interface Shape {
//  double perimeter();
  void accept(ShapeVisitor visitor);
}

