package victor.training.java.instanceOf;

public class InstanceOf {
  public static void main(String[] args) {
    Position position = new Position(1, 2, 3);
    Color color = new Color(255, 255, 255, 1);
    Point point = new Point(position, color);

    method(point);
  }

  record Color(int r, int g, int b, float a) {
  }
  record Point(Position position, Color color) {
  }
  record Position(int x,int y, int z) {
  }
  
  private static void method(Object obj) {
    if (obj instanceof Point) {
      Point point = (Point) obj;
      Position position = point.position();
      System.out.println("At position = " + position);
      float a = point.color().a();
      System.out.println("Transparency:" + a);
      System.out.println("R=" + point.color().r());
      System.out.println("G=" + point.color().g());
      System.out.println("B=" + point.color().b());
    }
  }
}

