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

  private static void method(Object obj) {
//    if (obj instanceof Point(Position ...)) {
    if (obj instanceof Point point) {
      Position position = point.position();
      System.out.println("At position = " + position);

      Color color = point.color();
      System.out.println("⍺=" + color.a());
      System.out.println("R=" + color.r());
      System.out.println("G=" + color.g());
      System.out.println("B=" + color.b());
    }
  }
}

