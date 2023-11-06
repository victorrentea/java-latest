package victor.training.java.records;


class Dot {
  private int x;
  private int y;

  public int getX() {
    return x;
  }

  public Dot setX(int x) {
    this.x = x;
    return this;
  }

  public int getY() {
    return y;
  }

  public Dot setY(int y) {
    this.y = y;
    return this;
  }
}

public class RecordExercise {
  public static void main(String[] args) {
    Dot dot = new Dot();
    dot.setX(1);
    dot.setY(2);

    // TODO 1 make Dot a record
    // TODO 2 implement Dot.translate(int deltaX, int deltaY):Dot; Hint: you'll have to create a new instance
    //   eg new Dot(1,2).translate(3,4).equals(new Dot(4,6))
    // TODO 3 validate x and y < 1000 in constructor !!!
    // TODO 4 create overloaded constructor accepting x and y as strings! you will have to call this(int,int) aka "canonical" constructor
    // TODO 5 change the default toString to print itself in the format (x,y)

    System.out.println("At x = " + dot.getX());
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

