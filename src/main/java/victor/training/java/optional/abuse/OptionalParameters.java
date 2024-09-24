package victor.training.java.optional.abuse;

public class OptionalParameters {

  public void callers() {
    // without
    sendMessage("jdoe", "message");

    // with
    sendMessage("jdoe", "message", "REGLISS");
  }

  // ⬇⬇⬇⬇⬇⬇ utility / library code ⬇⬇⬇⬇⬇⬇
  public void sendMessage(String recipient, String message) {
    System.out.println("Resolve phone number for " + recipient);
    System.out.println("Send message " + message);
  }

  public void sendMessage(String recipient, String message, String trackingRegistry) {
    sendMessage(recipient, message);

    System.out.println("Notify the tracking registry : " + trackingRegistry);
  }
}
