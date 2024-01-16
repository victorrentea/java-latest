package victor.training.java.records;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0) // random
public class IntegrationTest {
  @Autowired
	private WebTestClient webClient;

  @Test
  void createBook() throws Exception {
    webClient.post().uri("/books")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "title":"name",
              "authors":["author1"],
              "teaserVideoUrl": null
            }
            """)
        .exchange()
        .expectStatus().isOk();
  }

  @Autowired
  WireMockServer wireMock;

  @Test
  void wireMockStubbing() {
    stubEmails("a@b.com");
  }

  private void stubEmails(String mail) {
    wireMock.stubFor(get(urlMatching("/emails"))
        .willReturn(okJson(STR."""
            {
              "emails":["\{mail}"]
            }
            """)));
  }
}
