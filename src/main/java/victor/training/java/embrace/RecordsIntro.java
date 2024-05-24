package victor.training.java.embrace;

import lombok.Value;

record Point(int x, int y) {
}
@Value
class PointL {
  int x; int y;
}


public class RecordsIntro {
  public static void main(String[] args) {
    Point point = new Point(1,2 );
    System.out.println(point + " has x: " + point.x());
  }
}

