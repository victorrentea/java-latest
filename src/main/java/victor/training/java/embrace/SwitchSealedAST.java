package victor.training.java.embrace;

import victor.training.java.embrace.Expr.Const;
import victor.training.java.embrace.Expr.Prod;
import victor.training.java.embrace.Expr.Sum;

public class SwitchSealedAST { // Abstract Syntax Tree
  public static void main() {
    // TODO 2 * 3 + 4
    Expr e =new Sum(
        new Prod(new Const(2), new Const(3)),
        new Const(4));

    System.out.println(str(e) + " = " + eval(e));
  }
  static int eval(Expr e) {
    return switch (e) {
      case Const(int value) -> value;
      case Prod(Expr left, Expr right) -> eval(left) * eval(right);
      case Sum(Expr left, Expr right) -> eval(left) + eval(right);
    };
  }

  static String str(Expr e) {
   return switch (e) {
      case Const(int value) -> Integer.toString(value);
      case Prod(Expr left, Expr right) -> str(left) + "*" +str(right);
      case Sum(Expr left, Expr right) -> str(left) + "+" + str(right);
    };
  }
}
sealed interface Expr {
  record Const(int value) implements Expr {}
  record Prod(Expr left, Expr right) implements Expr {}
  record Sum(Expr left, Expr right) implements Expr {}
}

