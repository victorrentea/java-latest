package victor.training.java.embrace;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class RecordsMicroTypes {

   //region source of data
   public Map<Long, Set<Tuple2<String, Integer>>> extremeFP() {
     return Map.of(1L, Set.of(
          Tuple.tuple("Table", 2),
          Tuple.tuple("Chair", 4)
      ));
   }
   //endregion
   
   void lackOfAbstractions() {
      Map<Long, Set<Tuple2<String, Integer>>> map = extremeFP();
      // 🚫Don't use 'var' above

      for (Long cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.v2 + " of " + t.v1)
             .collect(joining(", "));
         System.out.println("cid=" + cid + " got " + pl);
      }
   }

   void printCart(long cid, String c) {
      // etc
   }

}
