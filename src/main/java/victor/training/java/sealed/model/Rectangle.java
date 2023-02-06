package victor.training.java.sealed.model;

import lombok.Value;
import victor.training.java.sealed.ShapeVisitor;

public record Rectangle(int w, int h) implements Shape { // final classes
}
