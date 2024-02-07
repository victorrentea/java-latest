package victor.training.java.records;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    webClient.post().uri("/books")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\n" +
                   "  \"title\":\"name\",\n" +
                   "  \"authors\":[\"author1\"],\n" +
                   "  \"teaserVideoUrl\": null\n" +
                   "}\n")
        .exchange()
        .expectStatus().isOk();
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
