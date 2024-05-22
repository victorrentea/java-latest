package victor.training.java.sealed.expr;

public class ExprPlay {
  public static void main() {
    Expr expr1 = null; // TODO 2 * 3 + 4 - 5
    System.out.println(print(expr1));
    System.out.println(eval(expr1));
  }
  static int eval(Expr expr) {
    return 0; // TODO
  }
  static String print(Expr expr) {
    return "TODO"; // TODO
  }
}
interface Expr {

}

