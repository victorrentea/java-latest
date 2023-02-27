package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public class Immutable {
  private final String name;
  private final Other other;
  private final List<Integer> list;

  public Immutable(String name, Other other, List<Integer> list) {
    this.name = name;
    this.other = other;
    this.list = list;
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
  public List<Integer> getList() {
    return new ArrayList<>(list);
  }

  @Override
  public String toString() {
    return "Immutable{" +"name='" + name + '\'' +", other=" + other +", list=" + list +'}';
  }
}