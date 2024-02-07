package victor.training.java.records;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static java.util.stream.Collectors.joining;

public class MicroTypes {

  public Map<CustomerId, List<ProductQuantity>> extremeFP() {
    return Map.of(new CustomerId(1L), List.of(
        new ProductQuantity("Table", 2),
        new ProductQuantity("Chair", 4)
    ));
  }

  record CustomerId(long id){}
    // canonicalization
//    WeakHashMap<CustomerId, String> cache = new WeakHashMap<>();

//  } // huh?! what's this?! = microtype
  private record ProductQuantity(String name, int quantity){}
  @Test
  void lackOfAbstractions() {
//    Map.Entry<String, Integer>
//    Map<CustomerId, List<ProductQuantity>> map = extremeFP();
    Map<CustomerId, List<ProductQuantity>> customerToProductQuantities = extremeFP();
    // "var" obscures semantics here

    for (CustomerId cid : customerToProductQuantities.keySet()) {
      String pl = customerToProductQuantities.get(cid).stream()
          .map(t -> t.quantity() + " of " + t.name())
          .collect(joining(", "));
      System.out.println("cid=" + cid + " got " + pl);
    }
  }

  public void goodVar() {
    ResponseEntity<List<String>> response = new RestTemplate().exchange(new RequestEntity<>(HttpMethod.POST, URI.create("http://some-url")), new ParameterizedTypeReference<List<String>>() {
    });
  }

}
