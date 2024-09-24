package victor.training.java.optional.annotations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import javax.annotation.Nullable;


public class Annotations {
  private static final MyMapper mapper = new MyMapper();

  public static void main(String[] args) {
    Parcel parcel = new Parcel();
    parcel.setDelivery(new Delivery(new Address(new ContactPerson("John"))));
    //    parcel.setDelivery(new Delivery(new Address(null)));
    //    parcel.setDelivery(null);

    DeliveryDto dto = mapper.convert(parcel);
    System.out.println(dto.recipientPerson);
    System.out.println(dto);
  }
}

class MyMapper {
//  @Nullable
  public DeliveryDto convert(Parcel parcel) {
    DeliveryDto dto = new DeliveryDto();
    if (parcel.getDelivery() != null &&
        parcel.getDelivery().getAddress().getContactPerson() != null) {
      dto.recipientPerson = parcel.getDelivery()
          .getAddress()
          .getContactPerson().getName().toUpperCase();
    }
    return dto;
  }
}

class DeliveryDto {
  public String recipientPerson;
}

class Parcel {
  @Nullable
  private Delivery delivery; // NULL until a delivery is scheduled

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }
}


class Delivery {
  @NotNull
  private Address address; // NOT NULL IN DB

  public Delivery(Address address) {
    this.address = address;
  }

  public void setAddress(Address address) {
    this.address = address; // TODO null safe
  }

  public Address getAddress() {
    return address;
  }
}

class Address {
  @Nullable
  private final ContactPerson contactPerson; // can be null if shipping to a company

  public Address(ContactPerson contactPerson) {
    this.contactPerson = contactPerson;
  }

  public ContactPerson getContactPerson() {
    return contactPerson;
  }
}

class ContactPerson {
  @NotNull
  private final String name; // NOT NULL

  public ContactPerson(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
