package victor.training.java.cf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class Barman {
  @Autowired
  private RestTemplate rest;

  @GetMapping("/drink")
  public DillyDilly drink() {
    System.out.println("HERE");
    long t0 = currentTimeMillis();

    //  🛑 independent tasks ran sequentially take too long. What TODO ?
    Beer beer = rest.getForObject("http://localhost:9999/beer", Beer.class);
    Vodka vodka = rest.getForObject("http://localhost:9999/vodka", Vodka.class);
    DillyDilly dilly = new DillyDilly(beer, vodka);

    log.info("HTTP thread blocked for {} durationMillis", currentTimeMillis() - t0);
    return dilly;
  }
}
