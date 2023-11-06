package victor.training.java.records;

import jakarta.persistence.*;
import lombok.Builder;
import org.aspectj.weaver.ast.Call;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;


class Dto1 {

}
class Dto2 extends Dto1{}

@Builder(toBuilder = true)
record UberRecord(
    String a,
    String b,
    String c,
    String d,
    String e,
    String f,
    Long i
)
// extends AltaClasa // ilegal pt ca ar putea mosteni campuri mutable
// record AltRecord // ilegal
  implements Callable<String> // implements e bun
{
  @Override
  public String call() throws Exception {
    return null;
  }
}

class AltaClasa {}

public class Records {
  public static void main(String[] args) {
//    new UberRecord(
//        args[0],
//        args[1],
//        args[2],
//        args[3],
//        args[4],
//        args[5],
//        1l); // greu de citit -> go to builder
    UberRecord request0 = UberRecord.builder()
        .a(args[0])
        .b(args[0])
        .c(args[0])
        .d(args[0])
        .e(args[0])
        .f(args[0])
        .i(1L)
        .build();

    UberRecord altR = new UberRecord(request0.a(), request0.b(), request0.c(), request0.d(), "altE", request0.e(), request0.i());

    // less error prone, more readable
    UberRecord altR2 = request0.toBuilder().e("altE").build();

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