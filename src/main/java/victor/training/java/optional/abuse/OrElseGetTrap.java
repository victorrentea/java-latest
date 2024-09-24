package victor.training.java.optional.abuse;

import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.of;

public class OrElseGetTrap {
  private record Transfer(String from, String to, int amount) {
  }

  // returns a empty Optional if the user has no money
  static Optional<Transfer> takeOwnMoney(String user) {
    System.out.println("Taking money from " + user);
    return of(new Transfer("Victor", "Vodafone", 100));
  }

  static Transfer takeCreditMoney(String user) {
    System.out.println("Borrowing⚠️ money for " + user);
    return new Transfer("Creditor", "Vodafone", 100);
  }

  public static void main(String[] args) {
    Optional<Transfer> opt = takeOwnMoney("user");
    // BUG: calls takeCreditMoney even if takeOwnMoney returned a value
//    Transfer transfer = opt.orElse(takeCreditMoney("user"));
    // RULE: don;t call functions in orElse
    Transfer transfer = opt.orElseGet(() -> takeCreditMoney("user"));

//    f(g()); // g() runs first and then its result is passed to f()

//    Transfer transfer = takeOwnMoney("user").orElse(null);
//    if (transfer == null) {
//      transfer = takeCreditMoney("user");
//    }


    // in java arguments are passed to methods by VALUE.
    // that is, before calling orElse JVM has to invoke takeCreditMoney("user") and only then pass the result to orElse
    // PROBLEM: calls takeCreditMoney even if takeOwnMoney returned a value
    System.out.println("Transfer: " + transfer);
  }
}
