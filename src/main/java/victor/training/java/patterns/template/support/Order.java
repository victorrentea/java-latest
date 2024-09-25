package victor.training.java.patterns.template.support;

import lombok.Data;

@Data
public class Order {
   private Long id;
   private Long customerId;
   private Double amount;
}
