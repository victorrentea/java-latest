package victor.training.java.sealed.shapes;

// - nu ai voie sa atingi aceasta clasa ; nici subclasele. (ca sunt intr-un jar/generate)
// - sau nu vrei (ti-e scarba ca-s mari deja), sau nu vrei sa aelrgi prin 10 fisiere
public sealed interface Shape
    permits Circle, Square, Elipsa, Rectangle{
//  double perimeter();
}
record Elipsa(int razaMica, int razaMare) implements Shape {

}

