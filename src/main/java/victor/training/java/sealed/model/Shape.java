package victor.training.java.sealed.model;


import victor.training.java.sealed.ShapeVisitor;

public interface Shape  {
    void accept(ShapeVisitor visitor);
}