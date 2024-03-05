package victor.training.java.records;

import lombok.Value;
import lombok.With;

public class Records {
  public static void main(String[] args) {
    Point point = new Point(1,2);

    Point point2 = execute(point);
    System.out.println(point2 + " has x=" + point2.x());
  }

  private static Point execute(Point point) {
    return point.withX(-1);
  }
}

record Point(int x, int y) {
  public Point withX(int x) {
    return new Point(x, y);
  }
}


// TODO 1 make Point a record
// TODO 2 implement Point.translate(int deltaX, int deltaY):Point; Hint: you'll have to create a new instance
//   eg new Point(1,2).translate(3,4).equals(new Point(4,6))
// TODO 3 validate x and y < 1000 in constructor !!!
// TODO 4 create overloaded constructor accepting x and y as strings! you will have to call this(int,int) aka "canonical" constructor
// TODO 5 change the default toString to print itself in the format (x,y)

// === Lessons: records is:
// immutable
// getters without "get" 🎉
// hashcode/equals + toString
// no extra fields besides signature
// can have extra methods or statics
// constructors: validation in default one + more overloaded ones
// override generated methods
// polymorphism: can implement, cannot extend
// withers

