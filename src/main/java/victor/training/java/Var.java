package victor.training.java;

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

public class Var {

  @Test
  public void heavyGenerics() {
    RequestEntity<Object> request = new RequestEntity<>(HttpMethod.POST, URI.create("http://some-url"));
    ResponseEntity<List<String>> response = new RestTemplate().exchange(request, new ParameterizedTypeReference<List<String>>() {
    });
    System.out.println(response.getBody());
  }

  void lackOfAbstractions() {
    Map<Long, List<Tuple2<String, Integer>>> map = extremeFP();

    for (Long cid : map.keySet()) {
      String pl = map.get(cid).stream()
          .map(t -> t.v2 + " of " + t.v1)
          .collect(joining(", "));
      System.out.println("cid=" + cid + " got " + pl);
    }
  }

  //region Source of data
  public Map<Long, List<Tuple2<String, Integer>>> extremeFP() {
    return Map.of();
  }
  //endregion
}
