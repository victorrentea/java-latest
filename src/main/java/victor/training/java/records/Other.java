package victor.training.java.records;

public class Other {
  private final String data;

  public Other(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  @Override
  public String toString() {
    return "Other{" +
           "data='" + data + '\'' +
           '}';
  }
}
