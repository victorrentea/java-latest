package victor.training.java.embrace;

import victor.training.java.embrace.Maybe.Some;

import java.util.Random;

import static victor.training.java.embrace.Maybe.*;

public class SwitchSealedSome {
  public static void main(String[] args) {
    switch (someRepoMethod()) {
      case Some(var data) -> System.out.println("Got: " + data);
      case None() -> System.out.println("Got nada");
    }
  }

  static Maybe<String> someRepoMethod() {
    boolean found = new Random().nextBoolean();
    return found ? new Some<>("data") : new None<>();
  }
}

sealed interface Maybe<T>{ // alternative to Optional<>?
  record Some<T>(T t) implements Maybe<T> {}
  record None<T>() implements Maybe<T> {}
}
