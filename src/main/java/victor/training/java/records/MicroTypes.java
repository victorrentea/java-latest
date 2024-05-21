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

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class MicroTypes {

   public Map<Long, List<Tuple2<String, Integer>>> extremeFP() {
      Long customerId = 1L;
      Integer product1Count = 2;
      Integer product2Count = 4;
      return Map.of(customerId, List.of(
          Tuple.tuple("Table", product1Count),
          Tuple.tuple("Chair", product2Count)
      ));
   }
   
   @Test
   void lackOfAbstractions() {
      Map<Long, List<Tuple2<String, Integer>>> map = extremeFP();
      // 🚫Don't "var" above

      for (Long cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.v2 + " of " + t.v1)
             .collect(joining(", "));
         System.out.println("cid=" + cid + " got " + pl);
      }
   }

   public void useVar() {
      ResponseEntity<List<String>> response = new RestTemplate().exchange(new RequestEntity<>(HttpMethod.POST, URI.create("http://some-url")), new ParameterizedTypeReference<List<String>>() {
      });
   }

}
