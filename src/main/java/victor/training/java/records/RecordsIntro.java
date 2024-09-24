package victor.training.java.records;

import lombok.Data;
import lombok.Value;

public class RecordsIntro {
  public static void main(String[] args) {
    Point point = new Point(1,1);
    darkLogic(point);
    // why immutability:
    // 1) unexpected side effect to the state of an argument
    // causing a Temporal Coupling with the next line
    // = you can grow afraid of passing your objects around
    // 2) avoid race conditions in multi-threaded code
    System.out.println(point);
  }

  private static void darkLogic(Point point) {
    darkerPlace(point);
  }

  private static void darkerPlace(Point point) {
//    point.setX(point.getX() + 1); // quick fix TO DO remove on Monday
  }
}

//@Data //🤬+@Entity = @Getter + @Setter + @ToString + @EqualsAndHashCode
@Value //💖 = @Data + all fields private final
class Point {
  int x;
  int y;
}

