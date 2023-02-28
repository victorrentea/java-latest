package victor.training.java.structuredconcurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@SpringBootApplication
public class Workshop {
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

  // TODO call getBookingOffers and getWeather in p01_parallel.
  @GetMapping("parallel")
  public BookingOffersDto p01_parallel() throws InterruptedException {
    List<String> offers = apiClient.getBookingOffers(1);
    String weather = apiClient.getWeather();
    return new BookingOffersDto(offers, weather);
  }

  // TODO call getBookingOffers and getWeather in p01_parallel. <-- idem as above
  //  After 500 milliseconds (aka p02_timeout),
  //  - throw error if getBookingOffers did not complete in time
  //  - default weather to "Probably Sunny" if getWeather did not complete
  @GetMapping("timeout")
  public BookingOffersDto p02_timeout() throws InterruptedException {
    return null; // copy-paste from above
  }

  // TODO same as above +
  //  Return only the offers that came back in the allocated time
  @GetMapping("timely-offers")
  public BookingOffersDto p03_timelyOffers() throws InterruptedException {
    List<String> offers1 = apiClient.getBookingOffers(1);
    List<String> offers2 = apiClient.getBookingOffers(2);
    String weather = apiClient.getWeather();
    List<String> allOffers = Stream.concat(offers1.stream(), offers2.stream()).toList();
    return new BookingOffersDto(allOffers, weather);
  }

  record BookingOffersDto(List<String> offers, String weather) {
  }
}
