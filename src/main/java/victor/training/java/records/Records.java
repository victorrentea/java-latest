package victor.training.java.records;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

class Point {
  private int x;
  private int y;

  // behold: traditional Java boilerplate 🤢🤢🤢🤢
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
