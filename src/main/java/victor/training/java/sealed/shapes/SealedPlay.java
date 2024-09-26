package victor.training.java.sealed.shapes;

public class SealedPlay {
  public static void main(String[] args) {
    S s = null ;
    f(s);
  }

  private static int f(S s) {
    return switch (s) {
      case R r -> r.i();
      case FC fc -> 1;
      case SC1 sc1 -> 4;
      case SC2 sc2 -> 5;
      // exhausbie
      case NSC nsc -> 2;

      //non-exhaustive
//      case NSC_SUBC1 nsc -> 3;
//      case NSC_SUBC2 nsc -> 3;
    };
  }
}


sealed interface S permits FC,R,SC,NSC {
  default int x() {
    return 0;
  }
}
record R(int i) implements S {}
final class FC implements S {}
non-sealed class NSC implements S {}
sealed class SC extends NSC implements S
    permits SC1,SC2 {}
final class SC1 extends SC {}
final class SC2 extends SC {}

class NSC_SUBC1 extends NSC{}
class NSC_SUBC2 extends NSC{}