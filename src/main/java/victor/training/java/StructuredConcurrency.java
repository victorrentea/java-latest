package victor.training.java;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

@SuppressWarnings("ALL")
@Slf4j
@RestController
@SpringBootApplication
public class StructuredConcurrency {

  @GetMapping("parallel")
  public BookingOffersDto offersAndWeather() throws InterruptedException {
    try (var scope = new ShutdownOnFailure()) {

      Future<List<String>> futureOffers = scope.fork(() -> getBookingOffers(1));

      Future<String> futureWeather = scope.fork(() -> getWeather());

      scope.join();

      return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
    }
  }
  public List<String> getBookingOffers(int providerId) {
    return new RestTemplate().getForObject("http://localhost:9999/booking-offers-" + providerId, List.class);
  }
  public String getWeather() {
    return new RestTemplate().getForObject("http://localhost:9999/weather", String.class);
  }

  record BookingOffersDto(List<String> offers, String weather) {
  }
}
