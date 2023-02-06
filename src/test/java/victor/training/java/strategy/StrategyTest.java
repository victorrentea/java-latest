package victor.training.java.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

  @Test
  void coversAllPossibleTypesOfMessages() {
    for (MessageType type : MessageType.values()) {
       new Strategy().handleMessageDePeMQ(new Message(type, ""));
      // TODO orice exceptie e ok, mai putin "NU ASTA IN PROD"
    }
  }
}