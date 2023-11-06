package victor.training.java.varie;

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

  public boolean expresiiBooleneCuInstanceOf(Object obj) {
    return obj instanceof Point p && p.color().a()< .2;

  }
  private static void method(Object obj) {
    if (obj instanceof Point point) { // instance of pattern matching
//      Point point = (Point) obj;
      Position position = point.position();
      System.out.println("At position = " + position);

      System.out.println("⍺=" + point.color().a());
      System.out.println("R=" + point.color().r());
      System.out.println("G=" + point.color().g());
      System.out.println("B=" + point.color().b());
    }
  }
}

