package victor.training.java.sealed;


// un supertip sealed trebuie sa-si numeasca/contina toate subtipurile
sealed public interface Shape permits Rectangle, Square, Circle {
}

