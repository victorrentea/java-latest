
package victor.training.java.optional;


import victor.training.java.optional.model.Customer;
import victor.training.java.optional.model.MemberCard;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class Optional_Intro {

  // clothes shop
  public static void main(String[] args) {
    // test with 10 points or no MemberCard
    System.out.println(getDiscountLine(new Customer(new MemberCard("bar", 60))));
    System.out.println(getDiscountLine(new Customer(new MemberCard("bar", 10))));
  }

  public static String getDiscountLine(Customer customer) {
    Discount discount = computeDiscount(customer.getMemberCard());
    if (discount != null)
      return "You got a discount of %" + discount.globalPercentage();
    else
      return "Earn more points to get a discount";
  }

  private static Discount computeDiscount(MemberCard card) {
    if (card.getFidelityPoints() >= 100) {
      return new Discount(5, Map.of());
    }
    if (card.getFidelityPoints() >= 50) {
      return new Discount(3, Map.of());
    }
    return Discount.NO_DISCOUNT; // Null Object Pattern: a non-instance value that represents the absence of an object
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

