package victor.training.java.sealed.expr;



import static victor.training.java.sealed.expr.Expr.*;

public class ExprPlay {
  public static void main(String[] args) {
    // = "1 + 2" = 3
    Expr expr1 = null; // TODO new Sum(new Const(1), new Const(2));
    System.out.println(print(expr1) + " => " + eval(expr1));

    // = "-5 + 2" = -3
    Expr expr2 = null; // TODO = new Sum(Neg(Const(5)), Const(2))
    System.out.println(print(expr2) + " => " + eval(expr2));

    // = "-5 + 2 * 7" = 9
    Expr expr3 = null; // TODO = new Sum(Neg(Const(5)),     Prod(Const(2),Const(7))     )
    System.out.println(print(expr3) + " => " + eval(expr3));
  }

  static int eval(Expr expr) {
    throw new RuntimeException("Not implemented"); // TODO implement
  }

  static String print(Expr expr) {
    return "TODO"; // TODO
  }
}
// TODO Model expressions using sealed classes, for example:
//  sealed interface Expr {record Const(int c) implements Expr {} ...}
sealed interface Expr /*permits Sum, Const, Prod, Neg*/ {
  record Sum(Expr a, Expr b) implements Expr {}
  record Const(int c) implements Expr {}
  record Prod(Expr a, Expr b) implements Expr {}
  record Neg(Expr e) implements Expr {}
}


