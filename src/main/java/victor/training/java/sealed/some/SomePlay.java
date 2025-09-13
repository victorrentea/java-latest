package victor.training.java.sealed.some;

import victor.training.java.sealed.some.Maybe.Some;

import java.util.Random;

import static victor.training.java.sealed.some.Maybe.*;

public class SomePlay {
  public static void main(String[] args) {
    Maybe<String> maybeEntity = findById(new Random().nextBoolean());
    switch (maybeEntity) { // Scala-like
      case Some(var data) -> System.out.println("Got: " + data);
      case None() -> System.out.println("Got nada/ciu-ciu");
    }
//    (Some)maybeEntity
//    optionalEntity.get()
  }

  static Maybe<String> findById(boolean id) {
    return id ? new Some<>("data") : new None<>();
  }
}

// alternative to Optional<>
sealed interface Maybe<T>{
  record Some<T>(T t) implements Maybe<T> {}
  record None<T>() implements Maybe<T> {}
}
