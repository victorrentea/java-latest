package victor.training.java.Switch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

  @Test
  void coversAllPossibleTypesOfMessages() {
    for (MessageType type : MessageType.values()) {


      try {
        new Strategy().handleMessage(new Message(type, "", false));
      } catch (Exception e) {
        if (e instanceof IllegalArgumentException && !e.getMessage().equals("NU ASTA IN PROD")) {
          continue;
        }
        fail("entered default: ");
      }
      // TODO orice exceptie e ok, mai putin "NU ASTA IN PROD"
    }
  }
}