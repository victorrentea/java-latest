package victor.training.java;

import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executors;

@SpringBootApplication
public class SpringApp {
  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customize() {
    // deserialization of ImmutableList
    return builder -> builder.modules(new GuavaModule());
  }

  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    // tell Tomcat to process each HTTP request in a new virtual thread
    return tomcat -> tomcat.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
  }

  @Bean
  public RestTemplate rest() {
    return new RestTemplate();
  }

  @Bean
  public WebClient webClient() {return WebClient.create();}
}
