package victor.training.java.sealed;


import org.springframework.data.geo.Circle;


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


