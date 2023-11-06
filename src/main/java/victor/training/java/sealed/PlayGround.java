package victor.training.java.sealed;

public class PlayGround {
  public void method(Letter l) {
//    switch (l) {
//      case AbsC c ->0;
//    }

  }
}


sealed interface Letter permits RA, RB, I, NonSealedC {}
record RA() implements Letter {}
record RB() implements Letter {}

sealed interface I extends Letter permits I1A{}
record I1A() implements I {}

// atipic:
non-sealed class NonSealedC implements Letter {
}
// nu pot fi folosite in switch matching:
class C1 extends NonSealedC {}
class C2 extends NonSealedC {}