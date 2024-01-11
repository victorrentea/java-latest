package victor.training.java.records;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Objects;

public class Records {
  public static void main(String[] args) {
    Point point = new Point();
    point.setX(1);
    point.setY(2);

    dark(point);
    System.out.println(point + " has x=" + point.getX());
  }

  private static void dark(Point point) {
    point.setX(-1);
  }
}

@Data
class Point {
  private int x;
  private int y;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Dot{x=" + x + ", y=" + y + '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return x == point.x && y == point.y;
  }
}

// TODO 1 make Dot a record
// TODO 2 implement Dot.translate(int deltaX, int deltaY):Dot; Hint: you'll have to create a new instance
//   eg new Dot(1,2).translate(3,4).equals(new Dot(4,6))
// TODO 3 validate x and y < 1000 in constructor !!!
// TODO 4 create overloaded constructor accepting x and y as strings! you will have to call this(int,int) aka "canonical" constructor
// TODO 5 change the default toString to print itself in the format (x,y)

// === Lessons: records is:
// immutable
// getters without "get" 🎉
// hashcode/equals + toString
// no extra fields besides signature
// can have extra methods or statics
// constructors: validation in default one + more overloaded ones
// override generated methods
// polymorphism: can implement, cannot extend
// withers

