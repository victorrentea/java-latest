package victor.training.java.records;


record Dot(int x, int y) {
  public Dot { // constructorul 'canonic' - ala standard
    if (x < 0 || y < 0) throw new IllegalArgumentException("negative !!Y&Q(&TQ&*RQ#^");
  }
  public Dot(String spec) { // Overloaded Constructors must delegate to the canonical one
    this(Integer.parseInt(spec.split(",")[0]), Integer.parseInt(spec.split(",")[1]));
  }

  public void method() { // DA poti metoda
//    x = 2; // NU compileaza -> campurile sunt FINALE
  }
//  final int z; // NU compileaza -> nu ai voie campuri decat prin "semnatura" clasei

  private static int i; // wow, da de ce ?
  public static void raceConditionTeDaAfara() {  i++; } // in context multithreading (pe server side) -> e jale

}
// autogenereaza : constructor, getter (fara "get-"), toString, hashCode/equals ~= @Value
public class RecordLab {
  public static void main(String[] args) {
    Dot dot = new Dot(1, 2);
    System.out.println(dot.x());
    System.out.println(dot);
    System.out.println(new Dot("1,2").equals(dot));
    dot.method();
//    new Dot(-1, 2); throws ca valideaza ctorul


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
