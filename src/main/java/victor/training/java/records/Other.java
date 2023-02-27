package victor.training.java.records;

import lombok.*;

@Data // = getter toString hashCode/equals,
// daca campurile sunt finale => constructor
// daca campurile sunt nefinale => setteri🤢
public class Other {
  private final String data;
}
