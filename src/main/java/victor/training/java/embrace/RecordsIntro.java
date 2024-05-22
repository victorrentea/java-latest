package victor.training.java.embrace;

import java.util.Objects;

public class RecordsIntro {
  public static void main(String[] args) {
    Point point = new Point();
    point.setX(1);
    point.setY(2);
    System.out.println(point);
  }
}

class Point {
  private int x;
  private int y;

  // traditional Java boilerplate 🤢🤢🤢🤢
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
    return "Point{x=" + x + ", y=" + y + '}';
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

