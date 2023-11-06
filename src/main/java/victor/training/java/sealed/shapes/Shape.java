package victor.training.java.sealed.shapes;


import java.awt.*;

public sealed interface Shape
    permits Circle, Square{
//  double perimeter();
//  void accept(ShapeVisitor visitor);
//  default void accept2(ShapeVisitor visitor) {
//    visitor.visit(this); // nu compileaza
//  }
}

//sealed interface AreLaturi
//  permits Square, Rectangle {
//  List<String> laturi();
//}