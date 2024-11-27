//package victor.training.java.sealed.some;
//
//import victor.training.java.sealed.some.Maybe.Some;
//
//import java.util.Random;
//
//import static victor.training.java.sealed.some.Maybe.*;
//
//public class SomePlay {
//  public static void main(String[] args) {
//    switch (someRepoMethod(new Random().nextBoolean())) {
//      case Some(var data) -> System.out.println("Got: " + data);
//      case None() -> System.out.println("Got nada");
//    }
//  }
//
//  static Maybe<String> someRepoMethod(boolean b) {
//    return b ?  new Some<>("data") : new None<>();
//  }
//}
//
//// alternative to Optional<>
//sealed interface Maybe<T>{
//  record Some<T>(T t) implements Maybe<T> {}
//  record None<T>() implements Maybe<T> {}
//}
