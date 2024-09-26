package victor.training.java.cf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class Barman {
  @Autowired
  private RestTemplate rest;

//  @FunctionalInterface
  interface BeerSupplier {
    Beer getBeer();
  }

  @GetMapping("/drink")
  public DillyDilly drink() {
    System.out.println("HERE");
    long t0 = currentTimeMillis();


    // in Performance training we'll explore the OLDer ways of doing this using Executors.
    //  🛑 independent tasks ran sequentially take too long. What TODO ?
    String beerType = "IPA";
    Beer beer = fetchBeer(beerType);
    Vodka vodka = fetchVodka();

    Runnable r = () -> fetchBeer(beerType);
    Supplier<Beer> s = () -> fetchBeer(beerType);
    Callable<Beer> c = () -> fetchBeer(beerType);
    BeerSupplier bs = () -> fetchBeer(beerType);


    DillyDilly dilly = new DillyDilly(beer, vodka);

    log.info("HTTP thread blocked for {} durationMillis", currentTimeMillis() - t0);
    return dilly;
  }

  private Vodka fetchVodka() {
    return rest.getForObject("http://localhost:9999/vodka", Vodka.class);
  }

  private Beer fetchBeer(String beerType) {
    String type = beerType;
    return rest.getForObject("http://localhost:9999/beer", Beer.class);
  }
}


// CACHING should **NEVER** be the first answer to "how can I improve performance".
// Try first:
// - call less network: bring more data in one call: /beverages?types=beer,vodka ===> [Beer, Vodka]
//     GET /products?ids=1,2,3,4,5,6,7,8,9,10 or [1,2,3,4,5,6,7,8,9,10] in the body
//     <== [Product1, Product2, Product3, Product4, Product5, Product6, Product7, Product8, Product9, Product10]
// - optimize the network calls
// - parallelize your work (CPU or network) after JFR profiling your flow

// There are only 2 hard things in Computer Science:
// cache invalidation and naming things.