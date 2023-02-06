package victor.training.java.records;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Immutable {
  private final String name;
  private final Other other;
  private final List<Integer> list;

  public Immutable(String name, Other other, List<Integer> list) {
    this.name = name;
    this.other = other;
    this.list = Collections.unmodifiableList(list);
  }

  public String getName() {
    return name;
  }

  public Other getOther() {
    return other;
  }

//  public List<Integer> getList() {
//    return new ArrayList<>(list);
//    // 1) ineficient cu mem - malloc;
//    // 2) minte apelantu : el poate crede ca a sters lista.
//  }

  // **
  public List<Integer> getList() {
    return list;
  }

  @Override
  public String toString() {
    return "Immutable{" +
           "name='" + name + '\'' +
           ", other=" + other +
           ", list=" + list +
           '}';
  }
}