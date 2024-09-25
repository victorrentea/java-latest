package victor.training.java.patterns.template.support;

import java.util.List;

public interface OrderRepo { // Spring Data FanClub
   List<Order> findByActiveTrue(); // 1 Mln orders ;)
}