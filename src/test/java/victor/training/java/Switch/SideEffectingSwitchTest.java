package victor.training.java.Switch;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import victor.training.java.patterns.strategy.Country;
import victor.training.java.patterns.strategy.SideEffectingSwitch;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SideEffectingSwitchTest {

  @ParameterizedTest
  @EnumSource(Country.class)
  void consumerCuTeste_handlesEveryCountryWithoutException(Country country) {
    var sut = new SideEffectingSwitch();
    var msg = new SideEffectingSwitch.Message(country, "2025-01-01");

    assertDoesNotThrow(()->sut.consumerCuTeste(msg));
  }
}