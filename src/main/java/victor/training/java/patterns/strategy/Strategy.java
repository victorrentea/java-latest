package victor.training.java.patterns.strategy;

import java.time.LocalDate;

enum CountryEnum {
  RO, ES, FR, UK, CN
}

record Parcel(
    CountryEnum originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

class Plain {
  public static void main(String[] args) {
    CustomsService service = new CustomsService();
    System.out.println("Tax for (RO,100,100) = " + service.calculateCustomsTax(
        new Parcel(CountryEnum.valueOf("RO"), 100, 100, LocalDate.now())));
    System.out.println("Tax for (CN,100,100) = " + service.calculateCustomsTax(
        new Parcel(CountryEnum.valueOf("CN"), 100, 100, LocalDate.now())));
    System.out.println("Tax for (UK,100,100) = " + service.calculateCustomsTax(
        new Parcel(CountryEnum.valueOf("UK"), 100, 100, LocalDate.now())));
  }
}

//@Service
//@Data
//@ConfigurationProperties(prefix = "customs")
class CustomsService {
  //	private Map<String, Class<? extends TaxCalculator>> calculators; // configured in application.properties 😮
  public double calculateCustomsTax(Parcel parcel) {
    // a switch on a string is a code smell
    // despite the fact that that string appears to have a finite number of values
    // we have it as a String
    switch (parcel.originCountry()) {
      case UK:
        return calculateUKTax(parcel);
      case CN:
        return calculateChinaTax(parcel);
      case FR:
      case ES:
      case RO:
        return calculateEUTax(parcel);
      default:
        throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
    }
  }

  private static double calculateEUTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }

  private static double calculateChinaTax(Parcel parcel) {
    return parcel.tobaccoValue() + parcel.regularValue();
  }

  private static double calculateUKTax(Parcel parcel) {
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    // a colleague adds some more code here
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

