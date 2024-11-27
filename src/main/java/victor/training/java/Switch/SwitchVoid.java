package victor.training.java.Switch;


class SwitchVoid {
  public void handleMessage(HRMessage message) {
    int useless = switch (message.type()) {
      case RAISE -> handleRaiseSalary(message.content());
      case PROMOTE -> handlePromote(message.content());
      case FIRE -> {
        if (message.urgent())
          yield handleDismissUrgent(message.content());
        else
          yield handleDismiss(message.content());
      }
      case HIRING -> handleHiring();
//      default ->
//        throw new IllegalArgumentException("Should never happen in prod: unknown message type" + message.type());
    };
  }

  private static int handleHiring() {
    System.out.println("We're hiring!");
    return 0;
  }

  private int handlePromote(String content) {
    System.out.println(":)");
    return 0;
  }

  private int handleDismissUrgent(String content) {
    System.out.println(":( !!");
    return 0;
  }

  private int handleDismiss(String content) {
    System.out.println(":( !!");
    return 0;
  }

  private int handleRaiseSalary(String content) {
    System.out.println(":)");
    return 0;
  }

}


record HRMessage(MessageType type, String content, boolean urgent) {
  enum MessageType {
    FIRE, RAISE, PROMOTE, HIRING
  }

}
