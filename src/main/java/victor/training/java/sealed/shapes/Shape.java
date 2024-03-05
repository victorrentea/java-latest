package victor.training.java.sealed.shapes;


// compilation fails if this interface is implemented
// by a class that is not explicitly permitted
public sealed interface Shape permits Circle, Square {
}

//record Rectangle() implements Shape {
//}

