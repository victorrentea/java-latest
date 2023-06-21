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
  record BookingOffersDto(List<String> offers, String weather) {

  }
  @GetMapping("parallel")
  public BookingOffersDto offersAndWeather() throws InterruptedException {
    try (var scope = new ShutdownOnFailure()) {

      Future<List<String>> futureOffers = scope.fork(() -> getBookingOffers(1));

      Future<String> futureWeather = scope.fork(() -> getWeather());

      scope.join();

      return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
    }
  }

  private final RestTemplate rest = new RestTemplate();

  public List<String> getBookingOffers(int providerId) {
    String url = "http://localhost:9999/booking-offers-" + providerId;
    return rest.getForObject(url, List.class);
  }
  public String getWeather() {
    String url = "http://localhost:9999/weather";
    return rest.getForObject(url, String.class);
  }



//  @Bean
//  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
//    // tell Tomcat to create a new virtual thread for every incoming request
//    return protocolHandler -> protocolHandler.setExecutor(
//        Executors.newVirtualThreadPerTaskExecutor());
//  }
}
