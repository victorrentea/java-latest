package victor.training.java.records;

import java.util.List;

//@Data
//class PotSiEu {
//  private String a,b,c;
//}
//@Slf4j
// ==> Lombok la putere pt ca Java e ffff verbose
//@Data (hate) = getter+SETTER🤮+tostring+hashcode+equals
//@Value (❤️) = toate campurile finale+ctor+getter+hashcode+equals+tostring
//class ImmutableLombok {
//  String name;
//  Other other;
//  List<Integer> list;
//}


//public class Immutable {
//  private final String name;
//  private final Other other;
////  private final ImmutableList<Integer> list;
//  private final List<Integer> list;
//
////  public Immutable(String name, Other other, ImmutableList<Integer> list) {
//  public Immutable(String name, Other other, List<Integer> list) {
//    this.name = name;
//    this.other = other;
//    this.list = List.copyOf(list);
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public Other getOther() {
//    return other;
//  }
//
//  // ** 1 new ArrayList
////  public List<Integer> getList() {
////    return new ArrayList<>(list);
////    // 1) ineficient cu mem - malloc;
////    // 2) minte apelantu : el poate crede ca a sters lista.
////  }
//
//  // ** 2: Collections.unmodifiableList
////  public ImmutableList<Integer> getList() {
////    return list;
////  }
//  // ** 3: Guava lib : ImmutableList
//  public List<Integer> getList() {
//    return list;
//  }
//// ** 4: java 10: List.copyOf sau .of
//  @Override
//  public String toString() {
//    return "Immutable{" +
//           "name='" + name + '\'' +
//           ", other=" + other +
//           ", list=" + list +
//           '}';
//  }
//}

// java 17 ftw!! -> de aia lupti cu managementul sa pui java 17.
// Java 11 e pentru biz (sa reduca factura de licente)
// Java 17 e pt dev
public record Immutable(String name, Other other, List<Integer> list) {
}

// asta e exact ca data class (kt), case class (scala), clase din TypeScript
// ==> Lumea se uita sa renunte la Lombok pentru ca au record.?? poate...