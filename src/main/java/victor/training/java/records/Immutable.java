package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import jakarta.persistence.Entity;
import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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


public class Immutable {
  private final String name;
  private final Other other;
//  private final ImmutableList<Integer> list;
  private final List<Integer> list;

//  public Immutable(String name, Other other, ImmutableList<Integer> list) {
  public Immutable(String name, Other other, List<Integer> list) {
    this.name = name;
    this.other = other;
    this.list = List.copyOf(list);
  }

  public String getName() {
    return name;
  }

  public Other getOther() {
    return other;
  }

  // ** 1 new ArrayList
//  public List<Integer> getList() {
//    return new ArrayList<>(list);
//    // 1) ineficient cu mem - malloc;
//    // 2) minte apelantu : el poate crede ca a sters lista.
//  }

  // ** 2: Collections.unmodifiableList
//  public ImmutableList<Integer> getList() {
//    return list;
//  }
  // ** 3: Guava lib : ImmutableList
  public List<Integer> getList() {
    return list;
  }
// ** 4: java 10: List.copyOf sau .of
  @Override
  public String toString() {
    return "Immutable{" +
           "name='" + name + '\'' +
           ", other=" + other +
           ", list=" + list +
           '}';
  }
}