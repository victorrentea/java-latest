package victor.training.java.records;

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
class Point {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }


  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof Point)) return false;
    final Point other = (Point) o;
    if (!other.canEqual((Object) this)) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Point;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  public String toString() {
    return "Point(x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}

