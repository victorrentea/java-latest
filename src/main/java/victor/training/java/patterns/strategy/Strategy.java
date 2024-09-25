package victor.training.java.patterns.strategy;

import java.time.LocalDate;

enum CountryEnum {
  RO, ES, FR, UK, CN
  ,IN,

//  UNKNOWN
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
        parse("RO")));
    System.out.println("Tax for (CN,100,100) = " + service.calculateCustomsTax(
        parse("CN")));
    System.out.println("Tax for (UK,100,100) = " + service.calculateCustomsTax(parse("In")));
//    System.out.println("Tax for (UK,100,100) = " + service.calculateCustomsTax(parse("ID")));
  }

  private static Parcel parse(String fromAJson) {
    return new Parcel(CountryEnum.valueOf(fromAJson.toUpperCase()), 100, 100, LocalDate.now());
  }
}

//@Service
//@Data
//@ConfigurationProperties(prefix = "customs")
class CustomsService {
  //	private Map<String, Class<? extends TaxCalculator>> calculators; // configured in application.properties 😮
  public double calculateCustomsTax(Parcel parcel) {
    var calculator = selectTaxCalculator(parcel.originCountry());
    return calculator.calculateTax(parcel);
  }

  private static TaxCalculator selectTaxCalculator(CountryEnum originCountry) { // it's a factory method
    return switch (originCountry) { // switch expression (returns a value)
      case UK -> new UKTaxCalculator();
      case CN, IN -> new ChinaTaxCalculator();
      case FR, ES, RO -> new EUTaxCalculator();
    };
  }
}
//@FunctionalInterface // optional, but tells the reader that this is a functional interface
  // that can (should) be passed as a lambda
interface TaxCalculator { // code smell if you define an interface that you implemented, but you never use anywhere with that
  double calculateTax(Parcel parcel);
}
class EUTaxCalculator implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }
}
class ChinaTaxCalculator implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    // serious > 50 lines of logic
    return parcel.tobaccoValue() + parcel.regularValue();
  }
}
class UKTaxCalculator implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

