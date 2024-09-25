package victor.training.java.patterns.strategy;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

enum CountryEnum {
  RO(EUTaxCalculator::calculateTax) {
    // DOABLE but weird
//    @Override
//    public void computeTax(Parcel parcel) {
//      System.out.println("OMG!!");
//    }
  },
  ES(EUTaxCalculator::calculateTax),
  // coupling+++ between enum and TaxCalculator
  FR(EUTaxCalculator::calculateTax),
  UK(UKTaxCalculator::calculateTax),
  CN(ChinaTaxCalculator::calculateTax),
  IN(ChinaTaxCalculator::calculateTax);

  public final TaxCalculator calculateTax;
  CountryEnum(TaxCalculator calculateTax) {
    this.calculateTax = calculateTax;
  }

//  public abstract void computeTax(Parcel parcel);

  public static CountryEnum fromIso(String fromAJson) {
    return valueOf(fromAJson.toUpperCase());
  }
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
    return new Parcel(CountryEnum.fromIso(fromAJson), 100, 100, LocalDate.now());
  }

}

//@Service
//@Data
//@ConfigurationProperties(prefix = "customs")
class CustomsService {
  //	private Map<String, Class<? extends TaxCalculator>> calculators; // configured in application.properties 😮
  public double calculateCustomsTax(Parcel parcel) {
//    var calculator = selectTaxCalculator(parcel.originCountry());

//    var calculator = calculators.get(parcel.originCountry());
//    return calculator.calculateTax(parcel);

    return parcel.originCountry().calculateTax.calculateTax(parcel);
  }
  private static final Map<CountryEnum, TaxCalculator> calculators = Map.of(
      CountryEnum.UK, UKTaxCalculator::calculateTax,
      CountryEnum.CN, ChinaTaxCalculator::calculateTax,
      CountryEnum.IN, ChinaTaxCalculator::calculateTax,
      CountryEnum.FR, EUTaxCalculator::calculateTax,
      CountryEnum.ES, EUTaxCalculator::calculateTax,
      CountryEnum.RO, EUTaxCalculator::calculateTax
  );

  private static TaxCalculator selectTaxCalculator(CountryEnum originCountry) { // it's a factory method
    return calculators.get(originCountry);
//    return switch (originCountry) { // switch expression (returns a value)
//      case UK -> UKTaxCalculator::calculateTax;
//      case CN, IN -> ChinaTaxCalculator::calculateTax;
//      case FR, ES, RO -> EUTaxCalculator::calculateTax;
//    };
  }
  {
    TaxCalculator c1 = new TaxCalculator() {
      @Override
      public double calculateTax(Parcel parcel) {
        return EUTaxCalculator.calculateTax(parcel);
      }
    };
    TaxCalculator c2b = (Parcel parcel) -> {return EUTaxCalculator.calculateTax(parcel);};
    TaxCalculator c2 = (parcel) -> {return EUTaxCalculator.calculateTax(parcel);};
    TaxCalculator c3 = (parcel) -> EUTaxCalculator.calculateTax(parcel);
    TaxCalculator c4 = parcel -> EUTaxCalculator.calculateTax(parcel);
    TaxCalculator c5 = EUTaxCalculator::calculateTax;
    // having a custom defined interface rather than using Function interface is better. It's more semantic reach rich.
    Function<Parcel, Double> f = EUTaxCalculator::calculateTax; // target typing allows
  }
}

@FunctionalInterface // optional, but tells the reader that this is a functional interface
  // that can (should) be passed as a lambda
interface TaxCalculator { // code smell if you define an interface that you implemented, but you never use anywhere with that
  double calculateTax(Parcel parcel);
}
class EUTaxCalculator {
  public static double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }
}
class ChinaTaxCalculator {
  public static double calculateTax(Parcel parcel) {
    // serious > 50 lines of logic
    return parcel.tobaccoValue() + parcel.regularValue();
  }
}
class UKTaxCalculator {
  public static double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

