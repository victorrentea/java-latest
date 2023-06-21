import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.constantConcurrentUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static java.time.Duration.ofSeconds;

public class WireMockGatling extends Simulation {
  public static void main(String[] args) {
    GatlingEngine.startClass(WireMockGatling.class);
  }

  {
    String host = "http://localhost:9998";

    setUp(scenario(getClass().getSimpleName()).exec(
            http("").get("/weather"))
//        .injectClosed(rampConcurrentUsers(200).to(1000).during(ofSeconds(7)))
            .injectClosed(constantConcurrentUsers(600).during(ofSeconds(7)))
      )

        .protocols(http.baseUrl(host));
  }
}
