package victor.training.java.sealed;


import org.springframework.data.geo.Circle;
import victor.training.java.sealed.Letter.A;
import victor.training.java.sealed.Letter.B;
import victor.training.java.sealed.Letter.X;
import victor.training.java.sealed.Letter.Y;


// un supertip sealed trebuie sa-si numeasca/contina toate subtipurile

//sealed public interface Shape permits Rectangle, Square, Circle {
//}

//sau sa-si contina subtipurile
sealed public interface Shape {
  record Circle(int radius) implements Shape {
  }
  record Rectangle(int w, int h) implements Shape {
  }
  record Square(int edge) implements Shape {
  }
}


sealed interface Letter {
  record A() implements Letter{}
  record B() implements Letter{}
  final class X implements Letter {}
  sealed class Y implements Letter permits Y2,Y3 {}
}

final class Y2 extends Y {}
non-sealed class Y3 extends Y {}

class Y3a extends Y3 {
}

class Play {
  public int method(Letter letter) {
    return switch (letter) {
//      case Y3 y3 -> 4;
//      case Y3a y3a -> 4;
      case Y y -> 1;
      case B b -> 2;
      case A b -> 3;
      case X b -> 4;
    };
  }
}