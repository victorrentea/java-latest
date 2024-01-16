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
    Parcel parcel = new Parcel(a[0], parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
    System.out.println(calculateTax(parcel));
  }

  public static double calculateTax(Parcel parcel) {
    double result = 0;
    switch (parcel.originCountry()) {
      case "UK":
        result = parcel.tobaccoValue() / 2 + parcel.regularValue();
        break;
      case "CN":
        result = parcel.tobaccoValue() + parcel.regularValue();
        break;
      case "RO":
        result = parcel.tobaccoValue() / 3;
        break;
    }
    return result;
  }
}

record Parcel(
    String originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

enum CountryEnum {
  RO,
  UK,
  CN,
}