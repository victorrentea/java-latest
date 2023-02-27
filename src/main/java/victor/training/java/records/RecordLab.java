package victor.training.java.records;


record Dot(int x, int y) {}
// autogenereaza : constructor, getter (fara "get-"), toString, hashCode/equals ~= @Value

public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot(1, 2);

    System.out.println(dot.x());
    System.out.println(dot);

    System.out.println(new Dot(1, 2).equals(dot));

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
