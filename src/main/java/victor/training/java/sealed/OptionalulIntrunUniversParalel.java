package victor.training.java.sealed;

import victor.training.java.sealed.Opt.None;
import victor.training.java.sealed.Opt.Some;

public class OptionalulIntrunUniversParalel {
  public static Opt<String> oMetodaCarePoateDaNimicInapoi() {
//    return new Some<>("ceva");
    return new None<>();
  }

  public static void main(String[] args) {
    switch (oMetodaCarePoateDaNimicInapoi()) {
      case Some<String>(Object v)-> System.out.println(v);
      case None<String>() -> System.out.println("None!");
    };
  }
}


sealed interface Opt<T> {
  record Some<T>(T value) implements Opt<T>{}
  record None<T>() implements Opt<T>{}
}
