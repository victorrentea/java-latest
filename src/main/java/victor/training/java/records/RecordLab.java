package victor.training.java.records;

/*final*/
record Dot(int x, int y) {
//  final int noExtraFieldAllowed; // ce-o sa pui in el ?!
  public Dot { // params deduced from record signature
    if (x < 0 || y < 0) throw new IllegalArgumentException();
  }
  public Dot(String spec) { // overloaded constructor
    this(Integer.parseInt(spec.split(":")[0]),
          Integer.parseInt(spec.split(":")[1]));
  }

  public Dot moveRight(int deltaX) {
    return new Dot(x + deltaX,y);
  }

  // {} // no instance init block allowed

  @Override public int x() { return x -1; } // override generated methods
}

public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot(1, 2);
    System.out.println("x=" + dot.x()); // getter without "get"
    System.out.println("Tostring generated: " + dot);
    System.out.println("Equals on all fields: " + dot.equals(new Dot(1, 2)));
    System.out.println(dot.hashCode());
    System.out.println(new Dot("1:2").hashCode());
    // new Punct(-1, 0); // throws
    System.out.println(dot.moveRight(3));
  }
}
// polymorphism
//record PunctCuMotz() extends Punct {} cannot extend a record!
