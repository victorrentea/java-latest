package victor.training.java.records;

import java.util.List;
import java.util.Objects;

public
class Immutable {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Immutable immutable = (Immutable) o;
    return Objects.equals(name, immutable.name) && Objects.equals(other, immutable.other) && Objects.equals(list, immutable.list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, other, list);
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