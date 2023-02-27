package victor.training.java.sealed;


import org.springframework.data.geo.Circle;
import victor.training.java.sealed.Letter.*;


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
  sealed abstract class Y implements Letter permits Y2,Y3 {}
  enum Vowels implements Letter {
    A,E,I,O,U
  }
}

final class Y2 extends Y {}
non-sealed class Y3 extends Y {}

class Y3a extends Y3 {
}

class Play {
  public void method(Letter letter) {

//    "x".repeat(1000_0000);
//    return switch (letter) {
////      case Y3 y3 -> 4;
////      case Y3a y3a -> 4;
//      case Y y -> 1;
//      case B b -> 2;
//      case A b -> 3;
//      case X b -> 4;
////      case Vowels.O -> 6;
//
//    };
  }
}