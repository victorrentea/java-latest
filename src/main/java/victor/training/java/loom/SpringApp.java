package victor.training.java.loom;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

@Slf4j
@SpringBootApplication
@RestController
public class SpringApp {

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  // Incepand cu spring boot 3.0 + Java 19:
  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    // tell Tomcat to create a new virtual thread for every incoming request
    return protocolHandler -> protocolHandler.setExecutor(
            Executors.newVirtualThreadPerTaskExecutor());
  }

}
