package victor.training.java.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@RestController
public class SpringApp {

  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  @Bean
  public RestTemplate rest() {
    return new RestTemplate();
  }

  @Bean
  public Executor virtualExecutor() {
    // TODO vrentea 12.09.2024: un executor spring wrapuind VT executor + lift de thread locals
    // TODO vrentea 12.09.2024: use java 22/23 + lombok upgrade
    return Executors.newVirtualThreadPerTaskExecutor();
  }

}
