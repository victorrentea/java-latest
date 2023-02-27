package victor.training.java.varie;

public class Marketing {

  //  var i; //NU camp
  //  public void method(var i) { //NU param

  private static final int I  = 10;
  public static void main(String[] args) {

    var i = 1; // lasa javac sa infere tipul variabilei local la COMPILARE (nu la runtime)!!
    i = 2;

    var j = "a"; // merge doar daca -l atribui de la inceput

    // static typed = var/metodele/param au un tip cunoscut de la inceput
    // dynamically typed e porcaria de mai jos.
    var doamneFereste = 1;
    //    doamneFereste = "surpriza!!";

    var var = "alb";
  }
}

// de folosit doar in unit teste.
// in codu de prod poate produce panica la diff cand nu te mai ajuta intelliJ
