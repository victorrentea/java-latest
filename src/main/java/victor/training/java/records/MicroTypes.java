package victor.training.java.records;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class MicroTypes {
   // @NotNull si prietenii din javax/jakarta.validation sunt verificate de altcineva
   public record ProductCount(/*@NotNull*/ String productName, int count) {
//      public ProductCount
   } // instead of Tuple2<String,Integer>

   public record CustomerId(long id) {} // MIcroTypes/ID Semantic

   public Map<CustomerId, List<ProductCount>> extremeFP() {
      CustomerId customerId = new CustomerId(1L);
      Integer product1Count = 2;
      Integer product2Count = 4;
      return Map.of(customerId, List.of(
          new ProductCount(null, product1Count),
          new ProductCount("Chair", product2Count)
      ));
   }

   @Test
   void lackOfAbstractions() {
      var x =1;
      var map = extremeFP();
      // var = local variable (!NU field, param)
      //    type inference (isi da seama javac), dar nu se poate schimba tipul acelei variabile ulterior
//      map = "1"; // asta merge in JS, dar Slava DOMNULUI nu in Java
//      var map = 2;

      // Joke: try "var" above :)
      // var LOVE: mai simplu de scris
      // var HATE: tipurile variab pot ajuta la intelegere

      for (CustomerId cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.count() + " of " + t.productName().toUpperCase())
             .collect(joining(", "));
         System.out.println("cid=" + cid.id() + " got " + pl);
      }
   }

   public void method() {
      ResponseEntity<List<String>> response = new RestTemplate().exchange(new RequestEntity<>(HttpMethod.POST, URI.create("http://some-url")), new ParameterizedTypeReference<List<String>>() {
      });
   }

}
