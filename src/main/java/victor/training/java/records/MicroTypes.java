package victor.training.java.records;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple12;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class MicroTypes {
      //      // Joke: try "var" above :)
      //      var i = 1; // var este un trick sa definesti o variab 'mai compact'. de ce au pus var in Java 11 ? de marketing. sa atraga si JS
      ////      i += "js"; // merge in JS dar, Slava Domnului, nu merge in Java pt ca java e tot limbaj statically typed.
      //      var var = "alb";
      //      // CAND SA FOLOSIM VAR; niciodata! poate doar intr-un @Test
      //
      //      System.out.println(i);

   public Map<CustomerId, List<ProductCount>> extremeFP() {
      Long customerId = 1L;
      Integer product1Count = 2;
      Integer product2Count = 4;
      return Map.of(new CustomerId(customerId), List.of(
          new ProductCount("Table", product1Count),
          new ProductCount("Chair", product2Count)
      ));
   }

   @Test
   void lackOfAbstractions() {
      Map<CustomerId, List<ProductCount>> map = extremeFP();
      // "Primitive Obsession" code smell= cand te joci cu int int int long long si uiti ce sunt. pt ca nu au SEMANTICA

      for (CustomerId id : map.keySet()) {
         String pl = map.get(id).stream()
             .map(t -> t.count() + " of " + t.productName())
             .collect(joining(", "));
         System.out.println("cid=" + id + " got " + pl);
      }
   }
}
// recorduile au fost adaugate in limbaj pentru a cobora bariera psiho  care ne oprea
//  sa creem noi clase

// Apropos: in arhitecturi software, o clasa mica imutabila cu hash/equals
//   implem pe toate campurile se numeste  VALUE OBJECT
record CustomerId(long id){}

record ProductCount(String productName, int count) { }