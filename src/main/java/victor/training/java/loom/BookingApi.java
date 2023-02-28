package victor.training.java.loom;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class BookingApi {
  private static final ExecutorService threadPool = Executors.newFixedThreadPool(20);
  RestTemplate rest = new RestTemplate();
  @GetMapping("booking")
  public String booking() throws ExecutionException, InterruptedException, IOException {

    try (ShutdownOnFailure scope = new ShutdownOnFailure()) { // daca oricare sub task crapa, intrerupele pe celelalte si termina cu exceptie
      // forkez executia : startez mai multe sub taskuri
      Future<String> futureOffer1 = scope.fork(() -> rest.getForObject("http://localhost:9999/booking-provider-1", String.class));
      Future<String> futureOffer2 = scope.fork(() -> rest.getForObject("http://localhost:9999/booking-provider-2", String.class));

      scope.join();
      // aici toate future-urile sunt terminate (cu succes sau exceptii)

      return futureOffer1.resultNow() + " + " + futureOffer2.resultNow();
    } // .close() se cheama automat
  }

}
record BookingOffersDto(List<String> offers, String weather) {}