package victor.training.java.sealed.shapes;

// - nu ai voie sa atingi aceasta clasa ; nici subclasele. (ca sunt intr-un jar/generate)
// - sau nu vrei (ti-e scarba ca-s mari deja)
public sealed interface Shape
    permits Circle, Square {
//  double perimeter();
}
class Elipsa implements Shape {

}

