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



    // TODO la ce sa avem grija inainte sa urmam sfatul intelliJ sa "convert to record" ?
    // - in loc de getX() o sa apara x() => dar n-ar trebui sa-i pese nimanui de cum numesti getterii
    // -
  }
}


// final
// fields
// methods
// constructors
// hashcode equals
// polymorphism
