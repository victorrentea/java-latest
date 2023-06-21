package victor.training.java.delay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@SpringBootApplication
@RestController
public class DelayResponsesApp {
  public static void main(String[] args) {
    SpringApplication.run(DelayResponsesApp.class, "--server.port=9998");
  }

  public static final Executor DELAYED_EXECUTOR = CompletableFuture.delayedExecutor(500, MILLISECONDS);

  @GetMapping("weather")
  public String weather() throws InterruptedException {
    Thread.sleep(500);
    return "Cloudy";
  }
//  @GetMapping("weather")
//  public CompletableFuture<String> weather() {
//    return CompletableFuture.supplyAsync(() -> "Cloudy", DELAYED_EXECUTOR);
//  }


  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    // tell Tomcat to create a new virtual thread for every incoming request
    return protocolHandler -> protocolHandler.setExecutor(
        Executors.newVirtualThreadPerTaskExecutor());
  }
}
