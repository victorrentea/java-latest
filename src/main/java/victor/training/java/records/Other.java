package victor.training.java.records;

//@Data // = getter toString hashCode/equals,
//// daca campurile sunt finale => constructor
//// daca campurile sunt nefinale => setteri🤢
//public class Other {
//  private final String data;
//}

//@Value // = toate campurile "private final" +
//// getter toString hashCode/equals constructor
//public class Other {
//  String data;
//}

import java.util.Objects;

public final class Other {
  private final String data;

  public Other(String data) {
    this.data = data;
  }

  public String getData() {
    return this.data;
  }

  @Override
  public String toString() {
    return "Other{" +
           "data='" + data + '\'' +
           '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Other other = (Other) o;
    return Objects.equals(data, other.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }
}
