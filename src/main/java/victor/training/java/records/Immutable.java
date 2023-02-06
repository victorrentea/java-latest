package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Immutable {
  private final String name;
  private final Other other;
  private final ImmutableList<Integer> list;

  public Immutable(String name, Other other, ImmutableList<Integer> list) {
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

  // ** 1
//  public List<Integer> getList() {
//    return new ArrayList<>(list);
//    // 1) ineficient cu mem - malloc;
//    // 2) minte apelantu : el poate crede ca a sters lista.
//  }

  // ** 2:
  public ImmutableList<Integer> getList() {
    return list;
  }
  // ** 3: Guava lib : ImmutableList


  @Override
  public String toString() {
    return "Immutable{" +
           "name='" + name + '\'' +
           ", other=" + other +
           ", list=" + list +
           '}';
  }
}