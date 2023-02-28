package victor.training.java.loom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;

@RestController
public class BookingApi {
  private static final ExecutorService threadPool = Executors.newFixedThreadPool(20);
  @GetMapping("booking")
  public String booking() throws ExecutionException, InterruptedException {
    RestTemplate rest = new RestTemplate();

    Future<String> futureOffer1 = threadPool.submit(() ->
            rest.getForObject("http://localhost:9999/booking-provider-1", String.class));

    Future<String> futureOffer2 = threadPool.submit(()->
            rest.getForObject("http://localhost:9999/booking-provider-2", String.class));

    return futureOffer1.get() + " + " + futureOffer2.get();
  }

}
record BookingOffersDto(List<String> offers, String weather) {}