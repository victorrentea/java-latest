package victor.training.java.strategy;

import java.time.LocalDate;

public record Parcel(String originCountry, double tobaccoValue, double regularValue, LocalDate date) {
}
