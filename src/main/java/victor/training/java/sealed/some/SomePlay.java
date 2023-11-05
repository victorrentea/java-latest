package victor.training.java.sealed.some;

import victor.training.java.sealed.some.Opt.Some;

import static victor.training.java.sealed.some.Opt.*;

public class SomePlay {
  public static void main(String[] args) {
    switch (someRepoMethod()) {
      case Some(var data) -> System.out.println("Got: " + data);
      case None() -> System.out.println("Got nada");
    }
  }

  public static Opt<String> someRepoMethod() {
    return new Some<>("data");
//    return new None<>();
  }
}

sealed interface Opt<T>{
  record Some<T>(T t) implements Opt<T> {}
  record None<T>() implements Opt<T> {}
}
