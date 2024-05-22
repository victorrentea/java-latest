package victor.training.java.embrace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class VirtualThreadsClient {
//  private final WebClient webClient;
  private final RestClient restClient;

  public record UserPreferences(String favoriteBeerType) {
  }

  public UserPreferences fetchPreferences() {
    return restClient.get()
        .uri("http://localhost:9999/user/preferences")
        .retrieve()
        .body(UserPreferences.class);
  }

  public record Beer(String type) {
  }

  public Beer fetchBeer(UserPreferences pref) {
    return restClient.get()
        .uri("http://localhost:9999/beer/{}", pref.favoriteBeerType())
        .retrieve()
        .body(Beer.class);
  }


  public record Vodka(String type) {
  }

  public Vodka fetchVodka() {
    return restClient.get()
        .uri("http://localhost:9999/vodka")
        .retrieve()
        .body(Vodka.class);
  }

  public record DillyDilly(Beer beer, Vodka vodka) {
  }
}
