package victor.training.java.Switch;


import java.util.function.BiConsumer;

class SwitchVoid {
  public void handleMessage(HRMessage message) {
    // 3 solutions to link behavior with enum so that javac checks you're not missing a case
    var useless = switch (message.type()) { // #1
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

  private static Void handleHiring() {
    System.out.println("We're hiring!");
    return null;
  }

  private Void handlePromote(String content) {
    System.out.println(":)");
    return null;
  }

  private Void handleDismissUrgent(String content) {
    System.out.println(":( !!");
    return null;
  }

  private Void handleDismiss(String content) {
    System.out.println(":( !!");
    return null;
  }

  private Void handleRaiseSalary(String content) {
    System.out.println(":)");
    return null;
  }

}


record HRMessage(MessageType type, String content, boolean urgent) {

  enum MessageType {
    FIRE /*{ // #2 java 5 kung-fu
      @Override
      void handleMessage(HRMessage message) {
        // static method. cannot use Guice/Spring injected services
        // can only use statics
      }
    }*/,
    RAISE,
    PROMOTE(SomeDIManagedBean::handlePromoteHonest), // #3
    HIRING;

//    abstract void handleMessage(HRMessage message); // #2

    //#3 Enum+FP kung-fu (Java 8)
    public final BiConsumer<SomeDIManagedBean,HRMessage> handlerFunction;

    MessageType(BiConsumer<SomeDIManagedBean, HRMessage> handlerFunction) {
      this.handlerFunction = handlerFunction;
    }
  }
}

class SomeDIManagedBean {
  public void method(HRMessage message) {
    message.type().handlerFunction.accept(this, message);
  }
  public void handlePromoteHonest(HRMessage message) {
    System.out.println(":)");
  }
}