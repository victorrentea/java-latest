package victor.training.java.embrace;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 9999)
public class IntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  void apiTest() throws Exception {
    mockMvc.perform(post("/books")
            .contentType("application/json")
            .content("""
        {
          "title":"name",
          "authors":["author1"],
          "teaserVideoUrl": null
        }
        """)
        )
        .andExpect(status().isOk()); // 200 OK
  }


  @Autowired
  WireMockServer wireMock;

  @Autowired
  VirtualThreadsClient client;

  @Test
  void wireMockStubbing() {
    wireMock.stubFor(get(urlMatching("/user/preferences"))
        .willReturn(okJson("""
            {
              "favoriteBeerType": "blond"
            }""")));
    assertThat(client.fetchPreferences().favoriteBeerType()).isEqualTo("blond");
  }
}
