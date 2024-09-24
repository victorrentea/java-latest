package victor.training.java.records;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public class RecordsIntro {
  public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Point point = new Point("-1", "1");
    Method internal = Point.class.getDeclaredMethod("internal");
    internal.setAccessible(true);
    internal.invoke(point);

    // 1) manual validation
    Validator validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Point>> violations = validator.validate(point);
    violations.forEach(System.out::println);

    // 2) in typical code, you would have a service method that receives a Point
    // and the method signature says @Validated/@Valid on the param
    // servive.f(point);
    // public void f(@Validated Point point) { // or @Valid
    // via Method interception (AOP) throwing an error if the object is not valid

    Point movedPoint = darkLogic(point);
    // why immutability:
    // 1) unexpected side effect to the state of an argument
    // causing a Temporal Coupling with the next line
    // = you can grow afraid of passing your objects around
    // 2) avoid race conditions in multi-threaded code
    System.out.println(movedPoint);
    System.out.println(movedPoint.x()); // getter in record does not have "get" prefix
  }

  private static Point darkLogic(Point point) {
//    point.setX(point.getX() + 1);
    Point newPoint = new Point(point.x() + 1, point.y());
    return newPoint;

  }
}

//@Data //🤬+@Entity = @Getter + @Setter + @ToString + @EqualsAndHashCode
//@Value //💖 = @Data + all fields private final
record Point( // ! the generated class is FINAL
    @Min(0)
    int x,
    @Min(0) // javax.validation / jakarta.validation
    int y
) implements Comparable<Point> {
  Point {
//    if (x < 0 || y < 0) {
//      throw new IllegalArgumentException("Negative coordinates are not allowed");
//    }
//    if (x<0) {x=-x;}// we are not re-assigning a field but rather messing up with the parameters
    // before they are assigned to final fields
  }

  //overloaded constructor MUST call the canonical constructor on the first line
  // try to avoid overloaded constructors, and instead create static factory methods
  Point(String x, String y) {
    this(Integer.parseInt(x), Integer.parseInt(y));
  }

  public static Point of(String x, String y) { //⭐
    int xx = Integer.parseInt(x);
    int yy = Integer.parseInt(y);
    return new Point(xx, yy);// call to the canonical constructor
  }
  private void internal() {
    System.out.println("Magic");
  }
//  @Override public int x() {return x * 2;} // not recommended
//  @Override public Optional<Integer> x() {return Optional.of(1);} // can't change the getter return type

  public Point mirrorOx() {
    return new Point(x, -y); // changed copy
  }
  public boolean isVisible() {
    return x > 0 && y > 0;
  }

  @Override
  public int compareTo(Point o) {
    return Integer.compare(x, o.x);
  }
}
// canonical examples of such small immutable Value Objects:
// - Money{Currency:currency, BigDecimal:amount}
// - DateRange
// - Point
// - Color
// - Range
// - Interval
// - Position
// - Fee{TYPE,Money:money}

//record Point3D extends Point (int z) {}
//class Point3D extends Point {}


class R {}
class B extends R {}
class C extends B {}

//class D extends B,C {} // not allowed in Java. possible in C#,C++,Scala
//class
//interface
record Transaction(
    String from,
    String to,
    int amount,
    Optional<Double> fee) {
//  @Override
//  public Optional<Double> fee() {
//    return Optional.ofNullable(fee);
//  }
}
// the fee will be NULL!!!!!!!! yeeeewwwwww