package victor.training.java.sealed.expr;


import static victor.training.java.sealed.expr.Expr.*;

public class ExprPlay {
  public static void main(String[] args) {
    // = "1 + 2"
    Expr expr1 = new Sum(new Const(1), new Const(2));
    System.out.println(print(expr1) + " => " + eval(expr1));

    // = "-5 + 2"
    Expr expr2 = new Sum(new Neg(new Const(5)), new Const(2));
    System.out.println(print(expr2) + " => " + eval(expr2));
//
//    // = "-5 + 2 * 7"
    Expr expr3 = new Sum(new Neg(new Const(5)), new Mul(new Const(2), new Const(7)));
    System.out.println(print(expr3) + " => " + eval(expr3));
  }
  static int eval(Expr expr) {
    return switch (expr) {
      case Const(int c) -> c;
      case Sum(Expr left, Expr right) -> eval(left) + eval(right);
      case Neg neg -> -1 * eval(neg.expr());
      case Mul(Expr left, Expr right) -> eval(left) * eval(right);
    };
  }
  static String print(Expr expr) {
    return switch (expr) {
      case Const(int c) -> String.valueOf(c);
      case Sum(Expr left, Expr right) -> print(left) + " + " + print(right);
      case Neg neg -> "-" + print(neg.expr());
      case Mul(Expr left, Expr right) -> print(left) + " * " + print(right);
    };
  }
}
// TODO Model expressions using sealed classes, for example:
//  sealed interface Expr {record Const(int c) implements Expr {} ...}
sealed interface Expr {
  record Const(int value) implements Expr {}
  record Sum(Expr left, Expr right) implements Expr {}
  record Neg(Expr expr) implements Expr {}
  record Mul(Expr left, Expr right) implements Expr {}
}

