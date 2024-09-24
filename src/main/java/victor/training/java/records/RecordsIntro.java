package victor.training.java.records;

public class RecordsIntro {
  public static void main(String[] args) {
    Point point = new Point(-1, 1);
    darkLogic(point);
    // why immutability:
    // 1) unexpected side effect to the state of an argument
    // causing a Temporal Coupling with the next line
    // = you can grow afraid of passing your objects around
    // 2) avoid race conditions in multi-threaded code
    System.out.println(point);
    System.out.println(point.x()); // getter in record does not have "get" prefix
  }

  private static void darkLogic(Point point) {
//    point.setX(point.getX() + 1); // quick fix TO DO remove on Monday
  }
}

//@Data //🤬+@Entity = @Getter + @Setter + @ToString + @EqualsAndHashCode
//@Value //💖 = @Data + all fields private final
record Point(
    int x,
    int y
) {
  Point {
//    if (x < 0 || y < 0) {
//      throw new IllegalArgumentException("Negative coordinates are not allowed");
//    }
    if (x<0) {x=-x;}// we are not re-assigning a field but rather messing up with the parameters
    // before they are assigned to final fields
  }

//  @Override public int x() {return x * 2;} // not recommended

  public Point mirrorOx() {
    return new Point(x, -y); // changed copy
  }
  public boolean isVisible() {
    return x > 0 && y > 0;
  }
}
// canonical examples of such small immutable Value Objects:
// - Money{Currency:currency, BigDecimal:amount}
// - DateRange
// - Point
// - Color
// - Range
// - Interval
// - Position
// - Fee{TYPE,Money:money}

