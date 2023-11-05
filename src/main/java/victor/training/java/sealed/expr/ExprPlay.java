package victor.training.java.sealed.expr;


import static victor.training.java.sealed.expr.Expr.*;

public class ExprPlay {
  public static void main(String[] args) {
    // = "1 + 2"
    var expr1 = new Sum(new Const(1), new Const(2));
    System.out.println(print(expr1) + " => " + eval(expr1));

    // = "-5 + 2"
    var expr2 = new Sum(new Neg(new Const(5)), new Const(2));
    System.out.println(print(expr2) + " => " + eval(expr2));

    // = "-5 + 2 * 7"
    var expr3 = new Sum(new Neg(new Const(5)), new Product(new Const(2), new Const(7)));
    System.out.println(print(expr3) + " => " + eval(expr3));
  }

  static int eval(Expr expr) {
//    throw new RuntimeException("Not implemented"); // TODO implement
    return switch (expr) {
      case Const(var c) -> c;
      case Neg(var a) -> -eval(a);
      case Sum(var a, var b) -> eval(a) + eval(b);
      case Product(var a, var b) -> eval(a) * eval(b);
    };
  }
  static String print(Expr expr) {
//    return "TODO"; // TODO
    return switch (expr) {
      case Const(var c) -> ""+c;
      case Neg(var a) -> "(-" +print(a) + ")";
      case Sum(var a, var b) -> print(a) +"+"+ print(b);
      case Product(var a, var b) -> print(a) +"*"+ print(b);
    };
  }
}

// TODO Model expressions using sealed classes, for example:
//  sealed interface Expr {record Const(int c) implements Expr {} ...}
sealed interface Expr {
  record Sum(Expr left, Expr right) implements Expr {}
  record Product(Expr left, Expr right) implements Expr {}
  record Neg(Expr expr) implements Expr {}
  record Const(int c) implements Expr {}
}
