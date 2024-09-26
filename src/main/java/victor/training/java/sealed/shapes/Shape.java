package victor.training.java.sealed.shapes;


public sealed interface Shape // you have to tell which types you
  // alow as subclases
  permits Circle, Rectangle, Square
{
  // or by placing all the subclases inside this type
}

