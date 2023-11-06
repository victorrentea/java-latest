package victor.training.java.sealed;

public class PlayGround {
  public void method(Letter l) {
//    var r = switch (l) {
//      case RA(int i) -> 0;
////      case NonSealedC() ->0;
//    };
//    var RA(i) = f();

  }

  private RA f() {
    throw new RuntimeException("Method not implemented");
  }
}


sealed interface Letter permits RA, RB, I, NonSealedC {}
record RA(int i) implements Letter {}
record RB() implements Letter {}

sealed interface I extends Letter permits I1A{}
record I1A() implements I {}

// atipic:
non-sealed class NonSealedC implements Letter {
}
// nu pot fi folosite in switch matching:
class C1 extends NonSealedC {}
class C2 extends NonSealedC {}