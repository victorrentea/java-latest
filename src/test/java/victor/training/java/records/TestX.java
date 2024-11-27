package victor.training.java.records;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestX {
  @Test
  public void method() {
    String actual = "{\"title\":\"%s\",\"authors\":[\"author1\"],\"teaserVideoUrl\": null}\n".formatted("name");

//    Assertions.assertThat(prettify(actual)).isEqualToIgnoringWhitespace(prettify(expected)); // might miss bugs
    Assertions.assertThat(prettify(actual)).isEqualTo(prettify("""
        {
          "title":"name",
          "authors":["auth or1"],
          "teaserVideoUrl": null
        }
        """));

//    assertThat(actual).isJsonEqualTo(expected); // possible to do

//    Assertions.assertThat(this).usingre
  }

  private static String prettify(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      Object json = objectMapper.readValue(rawJson, Object.class);
      rawJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rawJson;
  }
}
