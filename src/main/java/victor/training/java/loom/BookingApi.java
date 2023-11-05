package victor.training.java.loom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BookingApi {
  @GetMapping("booking")
  public BookingOffersDto booking() {
    RestTemplate rest = new RestTemplate();
    String bookingOffer1 = rest.getForObject("http://localhost:9999/booking-provider-1", String.class);
    String bookingOffer2 = rest.getForObject("http://localhost:9999/booking-provider-1", String.class);
    return null;
  }

}
record BookingOffersDto(List<String> offers, String weather) {}