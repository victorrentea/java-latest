package victor.training.java.instanceOf;

public class InstanceOf {
  public static void main(String[] args) {
    Position position = new Position(1, 2, 3);
    Color color = new Color(255, 255, 255, 1);
    Point point = new Point(position, color);

    method(point);
  }

  private static void method(Object obj) {
    if (obj instanceof Point(Position position, Color(int r, int g, int b, float a))) {
      System.out.println("At position = " + position);
      System.out.println("Transparency:" + a);
      System.out.println("R=" + r);
      System.out.println("G=" + g);
      System.out.println("B=" + b);
    }
  }
}
