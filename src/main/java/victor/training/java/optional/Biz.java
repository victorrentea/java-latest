package victor.training.java.optional;

import lombok.RequiredArgsConstructor;
import victor.training.java.optional.model.Customer;
import victor.training.java.optional.model.Order;

@RequiredArgsConstructor
public class Biz {
   private final Config config;

   public void applyDiscount(Order order, Customer customer) {
      System.out.println("START");
      if (order.getOfferDate().before(config.getLastPromoDate())) { // TODO inside
         int points = customer.getMemberCard().getFidelityPoints();
         order.setPrice(order.getPrice() * (100 - 2 * points) / 100);
         System.out.println("APPLIED DISCOUNT using " + customer.getMemberCard().getBarcode());
      } else {
         System.out.println("NO DISCOUNT");
      }
   }
}

