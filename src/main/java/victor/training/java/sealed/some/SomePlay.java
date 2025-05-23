package victor.training.java.sealed.some;

import victor.training.java.sealed.some.Maybe.Some;

import java.util.Random;

import static victor.training.java.sealed.some.Maybe.*;

public class SomePlay {
  public static void main(String[] args) {
    var maybe = someRepoMethod(new Random().nextBoolean());
    var r = switch (maybe) { // expression
      case Some(var data) -> "Got: " + data;
      case None() -> "Got nada";
    };
    System.out.println(r);
  }
  static Maybe<String> someRepoMethod(boolean b) {
    return b ?  new Some<>("data") : new None<>();
  }
}
// alternative to Optional<>
sealed interface Maybe<T>{
  record Some<T>(T t) implements Maybe<T> {}
  record None<T>() implements Maybe<T> {}
}
//final class Alta<T> implements Maybe<T>{}