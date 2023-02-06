package victor.training.java.instance_of;

public class InstanceOf {
  public static void main(String[] args) {
    Point point = new Point(new Position(1, 2, 3), new Color(255, 255, 255, 1));

    method(point);
  }

  private static void method(Point point) {
    if (point instanceof Point(Position position, Color(int r, int g, int b, float a) )) {
      System.out.println("Transparency:" + a);
      System.out.println("At position = " + position);
    }
  }
}
