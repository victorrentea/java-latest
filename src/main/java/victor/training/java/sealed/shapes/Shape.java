package victor.training.java.sealed.shapes;


public sealed interface Shape {
  // or by placing all the subclases inside this type
  record Circle(int radius) implements Shape {
  }
  record Rectangle(int width, int height) implements Shape {
  }
  record Square(int edge) implements Shape {
  }
}

//class A {}
//record R extends A{}

