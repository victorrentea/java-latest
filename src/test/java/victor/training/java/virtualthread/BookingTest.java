package victor.training.java.virtualthread;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest

public class BookingTest {

  private static WireMockServer wireMock = new WireMockServer(9999);

  @BeforeAll
  public static void startWireMock() {
    wireMock.start();
  }

  @Test
  void explore() {
    wireMock.stubFor(get(urlEqualTo("/booking-provider-1")).willReturn(
            aResponse().withFixedDelay(10).withBody("Booking1")));
    wireMock.stubFor(get(urlEqualTo("/booking-provider-2")).willReturn(
            aResponse().withFixedDelay(10).withBody("Booking2")));
    wireMock.stubFor(get(urlEqualTo("/weather")).willReturn(
            aResponse().withFixedDelay(10).withBody("Rain")));


  }
}
