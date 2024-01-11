package victor.training.java.Switch;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Double.parseDouble;


class SwitchExpression {
  public static void main(String[] args) {
    List<String> flatLines = List.of(
        "RO|100|100|2021-01-01",
        "CN|100|100|2021-01-01",
        "UK|100|100|2021-01-01"
//        ,"|100|100|2021-01-01" // calls for case null (java 21)
    );

    flatLines.forEach(SwitchExpression::process);
  }

  private static void process(String flatParcelLine) {
    String[] a = flatParcelLine.split("\\|");
    Parcel parcel = new Parcel(a[0], parseDouble(a[1]), parseDouble(a[2]), LocalDate.parse(a[3]));
    System.out.println(calculateTax(parcel));
  }

  public static double calculateTax(Parcel parcel) {
    double result = 0;
    switch (parcel.getOriginCountry()) {
      case "UK":
        result = parcel.getTobaccoValue() / 2 + parcel.getRegularValue();
        break;
      case "CN":
        result = parcel.getTobaccoValue() + parcel.getRegularValue();
        break;
      case "RO":
        result = parcel.getTobaccoValue() / 3;
        break;
    }
    return result;
  }
}

@Data
class Parcel {
  private final String originCountry;
  private final double tobaccoValue;
  private final double regularValue;
  private final LocalDate date;
}


enum CountryEnum {
  RO,
  UK,
  CN,
}

// explore: non-enhaustive vs default?

