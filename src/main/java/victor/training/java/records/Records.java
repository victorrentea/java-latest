package victor.training.java.records;

import lombok.Value;

public class Records {
  public static void main(String[] args) {
    Point point = new Point(1, 2);

    Point point2 = dark(point);
    System.out.println(point2 + " has x=" + point2.x());
  }

  private static Point dark(Point point) {
    return point.move(-2, 0);
  }
}

record Point(int x, int y) {
  public Point move(int dx, int dy) {
    return new Point(x + dx, y + dy);
  }
}
