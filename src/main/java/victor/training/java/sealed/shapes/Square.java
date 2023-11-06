package victor.training.java.sealed.shapes;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;

public record Square(int edge) implements Shape {
//  @Override
//  public double perimeter() {
//    // #1 OOP logica IN clasa
//    return 4 * edge;
//  }
}


//
//@Inheritance
//@Entity
////@Discr
//abstract class Ticket {
//  enum Type {
//    CUSTOMER,
//    INTERNAL,
//    RETURN
//  }
//  @Enumerated
//  Type type;
//
//}
//class CustomerTicket extends Ticket {
//
//}
//class InternalTicket extends Ticket {
//
//}
//class ReturnTicket extends Ticket {
//
//}