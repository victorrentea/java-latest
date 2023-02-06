package victor.training.java.records;

class Oarecare {
}
// let {x,y} = f();   in f() { return {x:1, y:2};}
// console.log(x)

/*automat:final*/
record Punct(int x, int y) {
//  final int nuAiVoieCampuriInPlus; // ce-o sa pui in el ?!
  public Punct { // deduce param din semnatura recordului
    if (x < 0 || y < 0) throw new IllegalArgumentException();
  }
  public Punct(String spec) { // overloaded constructor
    this(Integer.parseInt(spec.split(":")[0]),
          Integer.parseInt(spec.split(":")[1]));
  }

  public Punct mutaLaDreapta(int deltaX) {
    return new Punct(x + deltaX,y);
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
    System.out.println(punct.mutaLaDreapta(3));
  }
}
// polymorphism
//record PunctCuMotz() extends Punct {} NU pot extinde un record!

//RecordDisecat.main(null);
