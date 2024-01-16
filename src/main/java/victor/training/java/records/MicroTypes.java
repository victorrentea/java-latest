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

public class MicroTypes {

  public Map<Long, List<ProductCount>> extremeFP() {
    return Map.of(1L, List.of(
        new ProductCount("Table", 2),
        new ProductCount("Chair", 4)
    ));
  }

  record ProductCount(String name, int count) {}
  @Test
  void lackOfAbstractions() {
    Map<Long, List<ProductCount>> map = extremeFP();

    for (Long cid : map.keySet()) {
      String pl = map.get(cid).stream()
          .map(t -> t.count() + " of " + t.name())
          .collect(joining(", "));
      System.out.println("cid=" + cid + " got " + pl);
    }
  }

  public void goodVar() {
    ResponseEntity<List<String>> response = new RestTemplate().exchange(new RequestEntity<>(HttpMethod.POST, URI.create("http://some-url")), new ParameterizedTypeReference<List<String>>() {
    });
  }

}
