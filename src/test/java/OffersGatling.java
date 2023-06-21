import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static java.time.Duration.ofSeconds;

public class OffersGatling extends Simulation {
  public static void main(String[] args) {
    GatlingEngine.startClass(OffersGatling.class);
  }

  {
    String host = "http://localhost:8080";

    setUp(scenario(getClass().getSimpleName()).exec(
            http("").get("/offers"))
            .injectClosed(constantConcurrentUsers(500).during(ofSeconds(5)))
      )

        .protocols(http.baseUrl(host));
  }
}
