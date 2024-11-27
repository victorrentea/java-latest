package victor.training.java.Switch;


import java.time.LocalDate;
import java.util.stream.Stream;

import static java.lang.Double.parseDouble;

class SwitchExpression {
  public static void main(String[] args) {
    Stream.of(
        "RO|100|100|2021-01-01",
        "CN|100|100|2021-01-01",
        "FR|100|100|2021-01-01",
        "UK|100|100|2021-01-01"
    ).forEach(SwitchExpression::process);
  }

  // parsing (infrastructure)
  private static void process(String flatParcelLine) {
    String[] a = flatParcelLine.split("\\|");
    Parcel parcel = new Parcel(CountryEnum.valueOf(a[0]), parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
    System.out.println(calculateTax(parcel));
  }

  //  core domain logic
  public static double calculateTax(Parcel parcel) /*throws Exception*/ {
    double v = switch (parcel.originCountry()) { // switch expression(enum) idiom
      case UK -> computeUkTax(parcel);
      case CN -> {
        double dontSmoke = parcel.tobaccoValue();
        yield dontSmoke + parcel.regularValue();
      }
      case RO, FR, DE -> computeEUTax(parcel);

      // AVOID default in switch expression
      //default -> throw new IllegalArgumentException("Unknown country: " + parcel.originCountry());
//      default -> throw new Exception("Unknown country: " + parcel.originCountry());
    };
    System.out.println("AFTER SWITCH");
    return v;
  }

  private static double computeEUTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }

  private static double computeUkTax(Parcel parcel) {
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

record Parcel(
    CountryEnum originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

enum CountryEnum {
  RO,
  UK,
  CN,
  FR,
  DE
}

// explore: non-enhaustive vs default?
// case null: (java 21)