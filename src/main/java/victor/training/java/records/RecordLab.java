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

public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot();
  }
}

// final
// fields
// methods
// statics
// constructors: validation + overloaded
// hashcode equals
// override generated methods
// polymorphism
// withers

