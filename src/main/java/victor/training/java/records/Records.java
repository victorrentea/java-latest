package victor.training.java.records;

import lombok.Value;
import lombok.With;

public class Records {
  public static void main(String[] args) {
    Point point = new Point(1,2);
//    point.setX(1);
//    point.setY(2);

    point = dark(point);
    System.out.println(point + " has x=" + point.getX());
  }

  private static Point dark(Point point){return point.withX(-1);
  }
}

//@Data  //hate
@Value //love = @Data + all fields = private + final
// Pro: better signal/noise ratio for code
// Con: less control over the generated code, careless design
class Point {
  @With
  int x;
  int y;
}
