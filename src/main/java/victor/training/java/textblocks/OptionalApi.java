package victor.training.java.textblocks;

import victor.training.java.loom.Util;

import java.util.Optional;

public class OptionalApi {
  public static void main(String[] args) {
    Optional<String> opt= metodaDinVreunRepo(1);
    // daca n-am gasit in DB meu, cheama API extern sa obtii elementul
//    String resolved = opt.orElse(networkCallLaVecinu(1));
    String resolved = opt.or(() ->Optional.of(networkCallLaVecinu(1)))
//            .orElseThrow(() -> new RuntimeException("Mama ta care ma pui sa scriu asta ca n-am ce sa-ti spun,. nu-i.")) // 8
            .orElseThrow() // java 11
            ;


    System.out.println(resolved);
  }
  private static String networkCallLaVecinu(int id) {
    Util.sleepq(3000);
    return "de la vecinu";
  }
  private static Optional<String> metodaDinVreunRepo(int id) {
    return Optional.of("a");
  }
}
