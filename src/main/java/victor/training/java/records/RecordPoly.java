package victor.training.java.records;

public class RecordPoly {
}

interface Animal {
  void faZgomot();
}

// extends oricum era nashpa !
record Dog() implements Animal {
  @Override
  public void faZgomot() {
    System.out.println("Ham!");
  }
}

record Cat() implements Animal {
  @Override
  public void faZgomot() {
    System.out.println("Meow!");
  }
}