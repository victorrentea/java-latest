package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import lombok.With;

import java.util.List;

public class Records {
  public static void main(String[] args) {
    Point point = new Point(1,2, 3,ImmutableList.of());
//    point.setX(1);
//    point.setY(2);

    point = dark(point);
    System.out.println(point + " has x=" + point.x());
  }

  private static Point dark(Point point){return point.withX(-1);
  }
}

//@Data  //hate
//@Value //love = @Data + all fields = private + final
// Pro: better signal/noise ratio for code
// Con: less control over the generated code, careless design
record Point(@With int x,
             int y,
             int z,
             ImmutableList<String> strings) {

  public Point translate(int dx, int dy) {
    return new Point(x + dx, y + dy, z, strings);
  }

  public boolean noStrings() {
    return strings.isEmpty();
  }
  // generted: private final int x; private final int y; private final int z;
  // generted: public int x() {return x;}
  // generted: public int y() {return y;}
  // generted: public int z() {return z;}
  // generted: public Point withX(int x) {return new Point(x, y, z);}
}



class MutableOldOne{
  private String name;
  private int age;

  public String name() {
    return name;
  }

  public MutableOldOne name(String name) {
    this.name = name;
    return this;
  }

  public int getAge() {
    return age;
  }

  public MutableOldOne setAge(int age) {
    this.age = age;
    return this;
  }
}