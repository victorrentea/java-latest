package victor.training.java.patterns.strategy;

public class OMGDontDoThis {
  public static void main(String[] args) {
    System.out.println(Color.RED.getCode());
    Color.RED.setCode("FF0001");
    System.out.println(Color.RED.getCode());
  }
}

enum Color {
  RED("FF0000"),
  GREEN("00FF00"),
  BLUE("0000FF");
  private String code;

  Color(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  // DON'T DO THIS! EVER EVER.
  public void setCode(String newCodeWHY) {
    this.code = newCodeWHY;
  }
}