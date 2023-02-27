package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import lombok.Value;

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

  public List<Integer> getList() {
    return list;
  }

  @Override
  public String toString() {
    return "Immutable{" +"name='" + name + '\'' +", other=" + other +", list=" + list +'}';
  }
}