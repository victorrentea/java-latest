package victor.training.java.Switch;

public record HRMessage(MessageType type, String content, boolean urgent) {
  enum MessageType {
    DISMISS,
    RAISE,
    PROMOTE,
    HIRE
  }

}
