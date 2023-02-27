package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Immutable {
  private final String name;
  private final Other other;
  private final List<Integer> list;

  public Immutable(String name, Other other, List<Integer> list) {
    this.name = name;
    this.other = other;
    // ImmutableList.copyOf() // guava lib
    this.list = List.copyOf(list); // copiaza lista initiala si o tine minte ca imutabila.
  }

  public String getName() {
    return name;
  }

  public Other getOther() {
    return other;
  }

  // #1: intorc o copie; rau pt ca
  // 1) aloci memorie aiurea + GC
  // 2) misleading pt ca lasa pe caller sa creada ca a mers add()
//  public List<Integer> getList() {
//    return new ArrayList<>(list);
//  }

  // #2 hard core: ii returnezi o interfata mai restransa, care-i da voie doar sa faca for
//  public Iterable<Integer> getList() {
//    return list;
//  }

  // #3 decorator peste lista originala care blocheaza orice modificare Java <=8
//  public List<Integer> getList() {
//    return Collections.unmodifiableList(list); // callerul primeste o exceptie
//  }


  // #4 List.of / copyOf
  // + callerul primeste o exceptie
  // - aloca memorie la fiecare get
  // pe ctor e mai cool.
  public List<Integer> getList() {
    return list;
  }

  // #5 Guava collections (ImmutableList)

  @Override
  public String toString() {
    return "Immutable{" +"name='" + name + '\'' +", other=" + other +", list=" + list +'}';
  }
}