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
        .injectClosed(constantConcurrentUsers(1000).during(ofSeconds(5))))
        // in a pre-Java21/SpringBoot 3.x app, the application would collapse under 500 concurrent requests
        // starving the Tomcat's thread pool (default size=200)

        .protocols(http.baseUrl(host));
  }
}
