
package victor.training.java.optional;


import victor.training.java.optional.model.Customer;
import victor.training.java.optional.model.MemberCard;

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
    return null;
  }

  public record Discount(int globalPercentage, Map<String, Integer> categoryDiscounts) {
  }
}

