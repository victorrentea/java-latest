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
  void apiTest() throws Exception {
    mockMvc.perform(post("/books")
            .contentType("application/json")
            .content(jsonBody("name"))
        )
        .andExpect(status().isOk()); // 200 OK
  }

  private static String jsonBody(String name) {
//    String.format("""
//        {
//          "title":"%s",
//          "authors":["author1"],
//          "teaserVideoUrl": null
//        }
//        """, name);
    System.out.println(" ".repeat(10) + "x");
    return """
        {
          "title":"%s",
          "authors":["author1"],
          "teaserVideoUrl": null
        }
        """.formatted(name);

    // preview feat removed in 23
//    return STR."""
//        {
//          "title":"\{name}",
//          "authors":["author1"],
//          "teaserVideoUrl": null
//        }
//        """;
  }


  @Autowired
  WireMockServer wireMock;

  @Test
  void wireMockStubbing() {
    wireMock.stubFor(get(urlMatching("/emails"))
        .willReturn(okJson("""
            {
              "emails":["a@b.com"]
            }""")));
  }
}
