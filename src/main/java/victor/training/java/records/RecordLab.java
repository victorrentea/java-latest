package victor.training.java.records;


// in loc de Integer ca sa "nu ai dureri de cap" - @Ovidiu (NPE)
record Dot(int x, int y) {
  public Dot/*(int x, int y) = subintelesi*/ {
    System.out.println(x()); // soc! campul e inca 0. Recordul e in offside
    if(x >= 1000) {
      throw new IllegalArgumentException("Invalid X value (should be less than 1000)");
    }
//    x++;
    if(y >= 1000) {
      throw new IllegalArgumentException("Invalid X value (should be less than 1000)");
    }
//    this.x = x; // subintelesi
//    this.y = y;
  }

  public Dot(String x, String y) {
    this(Integer.parseInt(x), Integer.parseInt(y)); // canonical constructor
  }
}

public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot(2222,3333);

    // TODO 1 make Dot a record
    // TODO 2 implement Dot.translate(int deltaX, int deltaY):Dot = nu poti MODIFICA instanta originala, deci mutarea = creerea unui nou Dot
    //   eg new Dot(1,2).translate(3,4).equals(new Dot(4,6))
    // TODO 3 validate x and y < 1000 in constructor !!!
    // TODO 4 create overloaded constructor accepting x and y as strings! you will have to call this(int,int) // constructorul "canonic"
    // TODO 5 change the default toString to print itself in the format (x,y)

    System.out.println("At x = " + dot.x());
    System.out.println("dot = " + dot);
  }
}

// immutable
// no extra fields
// extra methods or statics ✅
// constructors: validation + overloaded
// hashcode/equals
// override generated methods
// polymorphism: can implement, cannot extend
// withers

