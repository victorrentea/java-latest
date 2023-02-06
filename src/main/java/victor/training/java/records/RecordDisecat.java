package victor.training.java.records;

class Oarecare {
}

/*automat:final*/
record Punct(int x, int y) {
  public Punct { // deduce param din semnatura recordului
    if (x < 0 || y < 0) throw new IllegalArgumentException();
  }
  public Punct(String spec) { // overloaded constructor
    this(Integer.parseInt(spec.split(":")[0]),
          Integer.parseInt(spec.split(":")[1]));
  }
  // {} // nu ai voie instance init block

  @Override public int x() { return x -1; } // poti suprascrie codul generat
}

public class RecordDisecat {
  public static void main(String[] args) {
    Punct punct = new Punct(1, 2);
    System.out.println("x=" + punct.x()); // getteri fara "get"
    // nu setteri
    System.out.println("Tostring generat: " + punct);
    System.out.println("Equals pe toate campurile: " + punct.equals(new Punct(1, 2)));

    System.out.println(punct.hashCode());
    System.out.println(new Punct("1:2").hashCode());
    System.out.println(new Oarecare().hashCode()); // by default hashCode pe orice Object intoarce un nr ce reprezinta REFERINTA UNICA A INSTANTEI JAVA
    // new Punct(-1, 0); // throws
  }
}
// polymorphism
//record PunctCuMotz() extends Punct {} NU pot extinde un record!

//RecordDisecat.main(null);
