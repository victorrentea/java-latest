package victor.training.java.Switch;


import lombok.Data;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Double.parseDouble;

class SwitchExpression {
  public static void main(String[] args) {
    Stream.of(
        "RO|100|100|2021-01-01",
        "CN|100|100|2021-01-01",
        "UK|100|100|2021-01-01"
    ).forEach(SwitchExpression::process);
  }

  // parsing (infrastructure)
  private static void process(String flatParcelLine) {
    String[] a = flatParcelLine.split("\\|");
    // mapper string din request/fisier -> enum
    Parcel parcel = new Parcel(CountryEnum.valueOf(a[0]), parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
    System.out.println(calculateTax(parcel));
  }

  //  core domain logic
  public static double calculateTax(Parcel parcel) {
//    parcel.originCountry().calculateTax() @robert
    return switch (parcel.originCountry()) {
      case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
      case CN -> parcel.tobaccoValue() + parcel.regularValue();
      case RO -> rux(parcel);
      // daca Parcel#date > 1 mai, / 2 + 10

      // daca fol switch(enum) ca expr=> NU PUNE default
      //default -> throw new IllegalStateException("Unexpected value: " + parcel.originCountry());
    };
  }

  private static double rux(Parcel parcel) {
    if (parcel.date().isBefore(LocalDate.parse("01.05.2025")))
      return parcel.tobaccoValue() / 3;
    return parcel.tobaccoValue() / 2 + 10;
  }
}

record Parcel(
    CountryEnum originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

enum CountryEnum {
  RO { //@robert
    @Override
    double calculateTax() {
      return 1;
    }
  },
  UK {
    @Override
    double calculateTax() {
      return 2;
    }
  },
  CN {
    @Override
    double calculateTax() {
      return 3;
    }
  };/*,
  DB*/
  abstract double calculateTax();
}

// explore: non-enhaustive vs default?
// case null: (java 21)