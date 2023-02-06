import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.constantConcurrentUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static java.time.Duration.ofSeconds;

public class BeerGatling extends Simulation {
  public static void main(String[] args) {
    GatlingEngine.startClass(BeerGatling.class);
  }

  {
    String host = "http://localhost:8080";

    setUp(scenario(getClass().getSimpleName()).exec(http("")
                            .get("/beer")
            )
            .injectClosed(constantConcurrentUsers(500).during(ofSeconds(5))))
            // poti observa aici un thread starvation issue: toate cele
            // 500 gramada care pot folosi doar 200 de threaduri cat are Tomcat default

            .protocols(http.baseUrl(host));
  }
}
