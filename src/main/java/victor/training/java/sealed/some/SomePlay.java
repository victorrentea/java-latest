package victor.training.java.sealed.some;

import victor.training.java.sealed.some.Opt.Some;

import static victor.training.java.sealed.some.Opt.*;

public class SomePlay {
  public static void main(String[] args) {
    switch (someRepoMethod()) {
      case Some(var data) -> System.out.println("Got: " + data);
      case None() -> System.out.println("Got nada");
    }


    switch (nebunie(true)) {
      case Try.Failed(var e) -> System.err.println(e);
      case Try.Success(var v) -> System.out.println(v);
    }
  }

  public static Opt<String> someRepoMethod() {
    return new Some<>("data");
//    return new None<>();
  }

  public static Try<String> nebunie(boolean bou) {
//    if (bou) throw new IllegalArgumentException(); // nu nu, ca nu e FP
    if (bou) return new Try.Failed<>(new IllegalArgumentException());
    return new Try.Success<>("valoare");
  }
}

sealed interface Opt<T>{
  record Some<T>(T t) implements Opt<T> {}
  record None<T>() implements Opt<T> {}
}

sealed interface Try<T>{
  record Success<T>(T t) implements Try<T> {}
  record Failed<T>(Exception e) implements Try<T> {}
}
