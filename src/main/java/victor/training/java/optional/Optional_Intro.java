
package victor.training.java.optional;


import victor.training.java.optional.model.Customer;
import victor.training.java.optional.model.MemberCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
public class Optional_Intro {

  // clothes shop
  public static void main(String[] args) {
    // test with 10 points or no MemberCard
    System.out.println(getDiscountLine(new Customer(new MemberCard("bar", 60))));
    System.out.println(getDiscountLine(new Customer(new MemberCard("bar", 10))));
  }

  public static String getDiscountLine(Customer customer) {
    // further reading: Stream<> and Optional<> are Monads (in FP philosophy)
    Optional<Discount> discountOptional = computeDiscount(customer.getMemberCard());

    Optional<Integer> gpOptional = discountOptional.map(discount -> discount.globalPercentage());

    Optional<String> strOptional = gpOptional.map(gp -> "You got a discount of %" + gp);

    // complex to read:
//    return strOptional.isPresent() ? strOptional.get() : "Earn more points to get a discount";
    return strOptional.orElse("Earn more points to get a discount");

    // -> only executes if discountOptional isPresent (not empty), has a Discount inside

//    StringBuffer message = new StringBuffer();
//    // Cons: 2 x -> as params
//    // Cons: Not following FP ideology: = CHANGES DATA in a ->: .append(), instead of returning
//    discountOptional.map(Discount::globalPercentage)
//        .ifPresentOrElse(
//            percentage -> message.append("You got a discount of %").append(percentage),
//            () -> message.append("Earn more points to get a discount")
//        );
//    return message.toString();

        // best way to use Optional is without .get or .orElseThrow or .isPresent
//    if (gpOptional.isPresent())
//      return "You got a discount of %" + gpOptional.get();
//    else {
//      return "Earn more points to get a discount";
//    }
  }

  private static Optional<Discount> computeDiscount(MemberCard card) {
    if (card.getFidelityPoints() >= 100) {
      return Optional.of(new Discount(5, Map.of()));
    }
    if (card.getFidelityPoints() >= 50) {
      return Optional.of(new Discount(3, Map.of())); // full box
    }
//    return Optional.ofNullable(null);
    return Optional.empty(); // en empty box
//    return Discount.NO_DISCOUNT; // Null Object Pattern: a non-instance value that represents the absence of an object
  }

  public record Discount(int globalPercentage, Map<String, Integer> categoryDiscounts) {
    public static final Discount NO_DISCOUNT = new Discount(0, Map.of());
  }

  // Null object pattern:
//    List<String> list = List.of(); // Null Object pattern for collection = natural null object.
  //  for (String s : list) { .. }

  // authorizer.authorize(action);
  // Null object for Authorizer: Authorizer.NULL_AUTHORIZER.authorize(action);
  // public static final Authorized NULL_AUTHORIZER =
  //     new Authorizer() { @Override public void authorize(Action action) {/*nothing here*/} }
  // inside the #authorize method does NO CHECK {}

//    // NEVER EVER LEAVE A COLLECTION NULL => but []
}

