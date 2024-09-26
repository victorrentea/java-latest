package victor.training.java.cf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;

@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ThreadPoolTaskExecutor poolBar() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadFactory(Thread.ofVirtual().factory()); // makes no sense
    // how many threads can be running at the same time
    int howMuchLoadCanTheOtherApiCanTake = 50;
    executor.setCorePoolSize(howMuchLoadCanTheOtherApiCanTake);
    executor.setMaxPoolSize(howMuchLoadCanTheOtherApiCanTake);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("bar-");
    executor.initialize();
    return executor;
  }
}
