package victor.training.java.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@RestController
public class SpringApp {

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    // tell Tomcat to create a new virtual thread for every incoming request
    // this has no LIMIT of the number of threads, because virtual threads are extremely LIGHT
    // ThreadPools will die. It makes NO SENSE recycling virtual threads.
    return protocolHandler -> protocolHandler.setExecutor(
            Executors.newVirtualThreadPerTaskExecutor());
  }

}
