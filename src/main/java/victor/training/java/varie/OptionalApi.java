//package victor.training.java.varie;
//
//import victor.training.java.Util;
//
//import java.util.Optional;
//
//public class OptionalApi {
//
//  public static void main(String[] args) {
//    new OptionalApi().fetchFromDBorRemote(1);
//  }
//
//  // TODO this code has bad performance. Why?
//  public String fetchFromDBorRemote(int id) {
//    Optional<Foo> dbOpt = repoFindById(id);
//
//    Foo resolved = dbOpt.orElse(networkCallToRemoteSystem(id));
//
//    return resolved.value();
//  }
//
//  private static Foo networkCallToRemoteSystem(int id) {
//    Util.sleepMillis(3000);
//    return new Foo("From API id=" + id);
//  }
//
//  private static Optional<Foo> repoFindById(int id) {
//    return Optional.of(new Foo("From DB id=" + id));
//  }
//
//  record Foo(String value) {}
//}
