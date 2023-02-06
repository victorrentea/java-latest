package victor.training.java.sealed.model;

import lombok.Value;
import victor.training.java.sealed.ShapeVisitor;

@Value
public class Rectangle implements Shape {
  int w,h;
  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this); //wtf !?!?!
  }
}
