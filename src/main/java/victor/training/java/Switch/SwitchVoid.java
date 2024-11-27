package victor.training.java.Switch;


class SwitchVoid {
  public void handleMessage(HRMessage message) {
    switch (message.type()) {
      case RAISE -> handleRaiseSalary(message.content());
      case PROMOTE -> handlePromote(message.content());
      case FIRE -> {
        if (message.urgent())
          handleDismissUrgent(message.content());
        else
          handleDismiss(message.content());
      }
      case HIRING -> System.out.println("We're hiring!");
      default ->
        throw new IllegalArgumentException("Should never happen in prod: unknown message type" + message.type());
    };
  }

  private void handlePromote(String content) {
  }

  private void handleDismissUrgent(String content) {
    System.out.println(":( !!");
  }

  private void handleDismiss(String content) {
    System.out.println(":( !!");
  }

  private void handleRaiseSalary(String content) {
    System.out.println(":)");
  }

}


record HRMessage(MessageType type, String content, boolean urgent) {
  enum MessageType {
    FIRE, RAISE, PROMOTE, HIRING
  }

}
