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

  // without spring:
//  @RegisterExtension
//  WireMockExtension wireMockExtension = new WireMockExtension(9999);

  @Autowired
	private WebTestClient webClient;

  @Test
  void createBook() throws Exception {
    sendCreate("name")
        .exchange()
        .expectStatus().isOk();
  }

  private WebTestClient.RequestHeadersSpec<?> sendCreate(String name) {
    return webClient.post().uri("/books")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "title":"%s",
              "authors":["author1"],
              "teaserVideoUrl": null
            }
            """.formatted(name));
  }

  @Autowired
  WireMockServer wireMock;

  @Test
  void wireMockStubbing() {
    wireMock.stubFor(get(urlMatching("/emails"))
        .willReturn(okJson("{\n" +
                           "  \"emails\":[\"a@b.com\"]\n" +
                           "}\n")));
  }
}
