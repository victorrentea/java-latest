package victor.training.java.sealed.expr;

public class ExprPlay {
  public static void main() {
    Expr e = new Expr.Const(2); // TODO 2 * 3 + 4 - 5
    System.out.println(print(e));
    System.out.println(eval(e));
  }
  static int eval(Expr e) {
    return switch (e){
      case Expr.Const(int value) -> value;
    };
  }
  static String print(Expr expr) {
    return null;
  }
}
sealed interface Expr {
  record Const(int value) implements Expr {}
}

