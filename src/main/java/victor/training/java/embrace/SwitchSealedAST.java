package victor.training.java.embrace;

import victor.training.java.embrace.Expr.*;

public class SwitchSealedAST {
  public static void main() {
    // TODO 2 * 3 + 4
    Expr e = new Sum(
        new Prod(new Const(2), new Const(3)),
        new Const(4));

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

