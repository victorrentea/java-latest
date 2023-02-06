package victor.training.java.structuredconcurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@SpringBootApplication
public class StructuredConcurrencyWorkshop {
  @Component
  @SuppressWarnings("unchecked")
  static class ApiClient {
    public List<String> getBookingOffers(int providerId) {
      return new RestTemplate().getForObject("http://localhost:9999/booking-offers-" + providerId, List.class);
    }

    public String getWeather() {
      return new RestTemplate().getForObject("http://localhost:9999/weather", String.class);
    }
  }

  @Autowired
  ApiClient apiClient;

  // TODO call booking offers and get weather in parallel
  @GetMapping("parallel")
  public BookingOffersDto parallel() throws InterruptedException {
    List<String> offers = apiClient.getBookingOffers(1);
    String weather = apiClient.getWeather();
    return new BookingOffersDto(offers, weather);
  }

  // TODO call booking offers and get weather in parallel. Allow max 500 millis to complete.
  //   return the offers retrieved (if any), error if offers timed out
  @GetMapping("timeout")
  public BookingOffersDto timeout() throws InterruptedException {
    // Hint: copy&adjust the solution from previous exercise
    List<String> offers = apiClient.getBookingOffers(1);
    String weather = apiClient.getWeather();
    return new BookingOffersDto(offers, weather);
  }

  // TODO run all
  @GetMapping("timely-offers")
  public BookingOffersDto timelyOffers() throws InterruptedException {
    List<String> offers1 = apiClient.getBookingOffers(1);
    List<String> offers2 = apiClient.getBookingOffers(2);
    String weather = apiClient.getWeather();
    List<String> allOffers = Stream.concat(offers1.stream(), offers2.stream()).toList();
    return new BookingOffersDto(allOffers, weather);
  }

record BookingOffersDto(List<String> offers, String weather) {
}
}
