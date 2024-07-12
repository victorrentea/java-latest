package victor.training.java.sealed.some;

import victor.training.java.sealed.some.Maybe.Some;

import java.util.Random;

import static victor.training.java.sealed.some.Maybe.*;

public class SomePlay {
  public static void main(String[] args) {
    Maybe<String> maybeString = someRepoMethod(new Random().nextBoolean());
    switch (maybeString) {
      case Some(var data) -> System.out.println("Got: " + data);
      case None() -> System.out.println("Got nada");
    }
  }

  static Maybe<String> someRepoMethod(boolean b) {
    return b ?  new Some<>("data") : new None<>();
  }
}

// alternative to Optional<>; tot in cap asta de FP extremista:
// Either(left,right), Try(rezultat,eroare)
sealed interface Maybe<T>{
  record Some<T>(T t) implements Maybe<T> {}
  record None<T>() implements Maybe<T> {}
}


sealed interface PaymentMethod {
  record CreditCard(String cardNumber) implements PaymentMethod {}
  record Cash() implements PaymentMethod {}
  record BankTransfer(String iban) implements PaymentMethod {}
//  record Bitc(String iban) implements PaymentMethod {}
}

