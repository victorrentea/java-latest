package victor.training.java.records;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0) // random
public class IntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  void createBook() throws Exception {
    mockMvc.perform(post("/books")
            .contentType("application/json")
            .content("{\n" +
                     "  \"title\":\"name\",\n" +
                     "  \"authors\":[\"author1\"],\n" +
                     "  \"teaserVideoUrl\": null\n" +
                     "}")
        )
        .andExpect(status().isOk()); // 200 OK
  }


  @Autowired
  WireMockServer wireMock;

  @Test
  void wireMockStubbing() {
    wireMock.stubFor(get(urlMatching("/emails"))
        .willReturn(okJson(getBody("a@b.com"))));
  }

  private String getBody(String email) {
    return """
               {
                 "emails":["%s"]
               }
               """.formatted(email);
  }
}
