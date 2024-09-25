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
    System.out.println("Tax for (UK,100,100) = " + service.calculateCustomsTax(parse("ID")));
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
//    switch (parcel.originCountry()) { // statement (does not return a value)
//      case UK:
//        return calculateUKTax(parcel);
//      case CN:
//        return calculateChinaTax(parcel);
//      case FR:
//      case ES:
//      case RO:
//        return calculateEUTax(parcel);
//      default:
//        throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
//    }
    double v = switch (parcel.originCountry()) { // switch expression (returns a value)
      case UK -> calculateUKTax(parcel);
      case CN,IN -> calculateChinaTax(parcel);
      case FR, ES, RO -> calculateEUTax(parcel);
      // compilation failure if you don't cover all ENUM values; much earlier, much cheaper to fix
//      default -> throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
      // a bad practice if you use switch as an expression on an ENUM
    };
    return v;
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

