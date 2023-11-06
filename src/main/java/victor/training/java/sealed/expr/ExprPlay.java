package victor.training.java.sealed.expr;



import static java.lang.String.valueOf;
import static victor.training.java.sealed.expr.Expr.*;

public class ExprPlay {
  public static void main(String[] args) {
    // = "1 + 2" = 3
    Expr expr1 = new Sum(new Const(1), new Const(2));
    System.out.println(print(expr1) + " => " + eval(expr1));

    // = "-5 + 2" = -3
    Expr expr2 = new Sum(new Neg(new Const(5)), new Const(2));
    System.out.println(print(expr2) + " => " + eval(expr2));

    // = "-5 + 2 * 7" = 9
    Expr expr3 = new Sum(new Neg(new Const(5)), new Prod(new Const(2), new Const(7)));
    System.out.println(print(expr3) + " => " + eval(expr3));
  }

  static int eval(Expr expr) {
   return switch (expr) {
     case Sum(Expr a, Expr b) -> eval(a) + eval(b);
     case Const(int c) -> c;
     case Neg(Expr e) -> -1 * eval(e);
     case Prod(Expr a, Expr b) -> eval(a) * eval(b);
   };
  }

  static String print(Expr expr) {
    return switch (expr) {
      case Sum(var a, var b) -> STR."\{print(a)} + \{print(b)}";
      case Prod(var a, var b) -> print(a) + "*" + print(b);
      case Const(var c) -> valueOf(c);
      case Neg(var e) -> "(-" + print(e) + ")";
    };
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


