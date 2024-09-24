package victor.training.java.optional;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Optional_Chain {
  private static final MyMapper mapper = new MyMapper();

  public static void main(String[] args) {
    Parcel parcel = new Parcel();
    parcel.setDelivery(new Delivery(new Address(new ContactPerson("John"))));
    //    parcel.setDelivery(new Delivery(new Address(null)));
    //    parcel.setDelivery(null);

    DeliveryDto dto = mapper.convert(parcel);
    System.out.println(dto);
  }
}

class MyMapper {
  public DeliveryDto convert(Parcel parcel) {
    DeliveryDto dto = new DeliveryDto();
//    if (parcel != null && // #life in legacy; terror; reality: half of these properties are required
//        parcel.getDelivery() != null &&
//        parcel.getDelivery().getAddress() != null &&
//        parcel.getDelivery().getAddress().getContactPerson() != null &&
//        parcel.getDelivery().getAddress().getContactPerson().getName() != null) {
//    dto.recipientPerson = parcel.getDelivery().getAddress().getContactPerson().getName().toUpperCase();
//    }

//    dto.recipientPerson = parcel?.getDelivery()?.getAddress()?.getContactPerson()?.getName()?.toUpperCase();

    dto.recipientPerson = Optional.ofNullable(parcel)
        .flatMap(Parcel::getDelivery)
        .map(Delivery::getAddress)
        .flatMap(address -> address.getContactPerson())
        .map(ContactPerson::getName)
        .map(String::toUpperCase)
        .orElse("Unknown");

    //a data model that TELLS you what attributes are optional and what are mandatory
    // burned in the model

//    parcel.getDelivery()
//        .map(delivery -> delivery.getAddress().getContactPerson())

    return dto;
  }
}

class DeliveryDto {
  public String recipientPerson;
}

// HUGE IMPACT on EXISTING CODE
class Parcel {
  private Delivery delivery; // NULL until a delivery is scheduled

  public Optional<Delivery> getDelivery() {
    return Optional.ofNullable(delivery);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }
}


class Delivery {
  private Address address; // NOT NULL IN DB

  public Delivery(Address address) {
    this.address = requireNonNull(address);
  }

  public void setAddress(Address address) {
    this.address = requireNonNull(address); // TODO null safe
  }

  public Address getAddress() {
    return address;
  }
}

class Address {
  private final ContactPerson contactPerson; // can be null if shipping to a company

  public Address(ContactPerson contactPerson) {
    this.contactPerson = contactPerson;
  }

  public Optional<ContactPerson> getContactPerson() /*throws IOException*/ {
    return Optional.ofNullable(contactPerson);
  }
}

class ContactPerson {
  private final String name; // NOT NULL

  public ContactPerson(String name) {
    this.name = requireNonNull(name);
  }

  public String getName() {
    return name;
  }
}
