package victor.training.java.records;

public class PlayString {
  public static void main(String[] args) {
    String s1 = """
        {
          "title":"name",
          "authors":["author1"],
          "teaserVideoUrl": null
        }
        """;
    String s2 = "{\n" +
                "  \"title\":\"name\",\n" +
                "  \"authors\":[\"author1\"],\n" +
                "  \"teaserVideoUrl\": null\n" +
                "}\n";
    System.out.println(s1);
    System.out.println(s1.equals(s2));
  }
}
