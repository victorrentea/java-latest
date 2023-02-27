package victor.training.java.varie;

import victor.training.java.Util;

import java.util.Optional;

public class OptionalApi {

  public static void main(String[] args) {
    fetchFromDBOrRemoteAPI(1);
  }

  private static void fetchFromDBOrRemoteAPI(int id) {
    Optional<Foo> fooOpt = repoFindById(id);



//    Foo foo1 = fooOpt.get(); // PROST< au gresit numele metodei asteia in Java8 ~> nu sugereaza ca arunca ex
//    Foo foo2 = fooOpt.orElseThrow(); // BUN (java 11) din cauza numelui - e mai bun!

    // if not found in my DB, fetch from external API
//    Foo resolved = fooOpt.orElse(networkCallToRemoteSystem(id));
    Foo resolved = fooOpt
            .or(() -> networkCallToRemoteSystem(id)) // executa functie daca era Optionalul empty.
//            .orElseThrow(()->new IllegalArgumentException("Java8")) // java 8
            .orElseThrow() // java11
            ;

    // TODO what's wrong with this?
    System.out.println(resolved);
  }

  private static Optional<Foo> networkCallToRemoteSystem(int id) {
    System.out.println("Performing expensive network call");
    Util.sleepMillis(3000);
    return Optional.of(new Foo("From API id=" + id));
  }
  private static Optional<Foo> repoFindById(int id) {
    return Optional.of(new Foo("From DB id=" + id));
  }

  record Foo(String value) {}
}
