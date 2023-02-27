package victor.training.java.records;

class Dot {
  private final int x;
  private final int y;

  Dot(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}

public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot(1, 2);

    System.out.println(dot.getX());

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
