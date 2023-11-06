package victor.training.java.records;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class Records {
  public static void main(String[] args) {
    User user = new User("John", Optional.empty());
    System.out.println(user.name());
    System.out.println(user);
    System.out.println(user.phone().orElse("N/A").toUpperCase());
  }
}

// unde ai voie sa folosesti Optional<>?
// DA: ca return type la metode ❤️ - de asta a fost inventat
// NU: ca param de metoda
// NU: ca field type
//  * ultimele NU, tolerate doar pt param de record

record User(String name,
            Optional<String> phone) {
//  @Override
//  public String name() {
//    throw new IllegalStateException(); // incalca Liskov Substitution Principle (soLid principle)
//  }

//  @Override
//  public String phone() {
//    return phoneOpt().orElseThrow();
//  }
//  public Optional<String> phoneOpt() {
//    return Optional.ofNullable(phone);
//  }



  //  @Override
//  public String toString() {
//    return "User{name🦄='" + name + "\'}";
//  }
}
// o clasa User in .class
// = final class => NU poate fi folosita pt Spring Bean
// = DA pt DTO
// + toate campurile sunt private FINAL String name; ==> NU pt @Entity JPA
//   insa DA pt @Embeddable JPA din Hibernate >= 6.2
// + constructor cu toti param din semnatura record
// + NU setter
// + getter ! fara "get" prefix; :( semnaturile getterelor nu pot fi modificate
// + hashCode/equals
// + toString - overridable

//class User {
//  private String name;
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//}

@Entity // = CUSTOMER{ID,FIRST_NAME,LAST_NAME, AGE}
class Customer {
  @Id
  @GeneratedValue
  private Long id;

  @Embedded
  private FullName fullName;
  private Integer age;
}
@Embeddable
record FullName(String firstName, String middleName, String lastName) {}