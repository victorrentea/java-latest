package victor.training.java.records;

public class Other {
  private String data;

  public Other(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public Other setData(String data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    return "Other{" +
           "data='" + data + '\'' +
           '}';
  }
}
