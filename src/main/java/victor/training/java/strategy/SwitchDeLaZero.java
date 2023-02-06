package victor.training.java.strategy;

public class SwitchDeLaZero {

  public static void main(String[] args) {
    int i = switch17("doi");
    System.out.println(i);
  }

  public static int switch17(String s) {
    return switch (s) {
      case "1" -> 1;
      case "doi" -> 2;
      default -> -1;
    };
  }
}
//SwitchDeLaZero.main(null);