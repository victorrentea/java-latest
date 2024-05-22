package victor.training.java.embrace;

public class SwitchSealedAST {
  public static void main() {
    // TODO 2 * 3 + 4
    Expr e = null;

    System.out.println(str(e) + " = " + eval(e));
  }
  static int eval(Expr e) {
    return 0;
  }

  static String str(Expr e) {
    return null;
  }
}
interface Expr {
}

