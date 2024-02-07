package victor.training.java;


import java.time.LocalDate;
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

  private static void process(String flatParcelLine) {
    String[] a = flatParcelLine.split("\\|");
    Parcel parcel = new Parcel(CountryEnum.valueOf(a[0]), parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
    System.out.println(calculateTax(parcel));
  }

  public static double calculateTax(Parcel parcel) {
    return switch (parcel.originCountry()) {
      case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
      case CN -> parcel.tobaccoValue() + parcel.regularValue();
      case RO -> parcel.tobaccoValue() / 3;
//      useless (anti-pattern) because the compiler cross-checks all the enum values
//      default -> throw new IllegalStateException("past 45 years Unexpected value: " + parcel.originCountry());
    };
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
//  TU
}