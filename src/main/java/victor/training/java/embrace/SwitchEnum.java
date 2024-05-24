package victor.training.java.embrace;


import java.time.LocalDate;
import java.util.stream.Stream;

import static java.lang.Double.*;

class SwitchEnum {

  //region infrastructure
  public static void main(String[] args) {
    Stream.of(
            "RO|100|100|2021-01-01",
            "CN|100|100|2021-01-01",
            "UK|100|100|2021-01-01"
        ).map(SwitchEnum::parse)
        .map(SwitchEnum::calculateTax)
        .forEach(System.out::println);
  }

  private static Parcel parse(String flatParcelLine) {
    String[] a = flatParcelLine.split("\\|");
    String countryStr = a[0];
    return new Parcel(CountryEnum.valueOf(countryStr), parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
  }
  //endregion

  //  domain logic
  public static double calculateTax(Parcel parcel) {
    return switch (parcel.originCountry()) {
      case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
      case CN -> parcel.tobaccoValue() + parcel.regularValue();
      case RO -> parcel.tobaccoValue() / 3;
      case PL -> MAX_VALUE;
    };
  }
}

record Parcel(
    CountryEnum originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date
) {
}

enum CountryEnum {
  RO,
  UK,
  PL,
  CN,
}