package victor.training.java.varie;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.Util;

import java.util.Optional;

public class OptionalApi {

  public static void main(String[] args) {
    Optional<Foo> fooOpt = repoFindById(1);
    // if not found in my DB, fetch from external API
    Foo resolved = fooOpt.orElse(networkCallToRemoteSystem(1));

    // TODO what's wrong with this?
    System.out.println(resolved);
  }
  private static Foo networkCallToRemoteSystem(int id) {
    Util.sleepMillis(3000);
    return new Foo("API");
  }
  private static Optional<Foo> repoFindById(int id) {
    return Optional.of(new Foo("DB"));
  }

  record Foo(String value) {}
}
