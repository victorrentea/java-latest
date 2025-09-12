package victor.training.java.sealed.shapes;

import java.util.Objects;

public final class Circle implements Shape {
  private final int radius;

  public Circle(int radius) {
    this.radius = radius;
  }

  public int radius() {
    return radius;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Circle) obj;
    return this.radius == that.radius;
  }

  @Override
  public int hashCode() {
    return Objects.hash(radius);
  }

  @Override
  public String toString() {
    return "Circle[" +
           "radius=" + radius + ']';
  }

}
