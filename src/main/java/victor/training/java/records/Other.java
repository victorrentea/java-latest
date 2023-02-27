package victor.training.java.records;

//@Data // = getter toString hashCode/equals,
//// daca campurile sunt finale => constructor
//// daca campurile sunt nefinale => setteri🤢
//public class Other {
//  private final String data;
//}

//@Value // = toate campurile "private final" + getter toString hashCode/equals constructor
//public class Other {
//  String data;
//}

public record Other(String data) {
}

// case class din Scala, data class din kt, php8, C#, typescript
