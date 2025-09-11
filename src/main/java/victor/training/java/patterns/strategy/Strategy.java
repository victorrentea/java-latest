package victor.training.java.patterns.strategy;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;


class Strategy {
  public static void main(String[] args) {
    CustomsService service = new CustomsService();
    Parcel ro = new Parcel(Country.RO, 100, 100, LocalDate.now());
    System.out.println("Tax for " + ro + " = " + service.calculateCustomsTax(ro));
    Parcel cn = new Parcel(Country.CN, 100, 100, LocalDate.now());
    System.out.println("Tax for " + cn + " = " + service.calculateCustomsTax(cn));
    Parcel uk = new Parcel(Country.UK, 100, 100, LocalDate.now());
    System.out.println("Tax for " + uk + " = " + service.calculateCustomsTax(uk));
  }
}

record Parcel(
    Country originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

@Service
@Data
class CustomsService {
  // 3 reguli pt un switch curat:
  // - case sa aiba o linie
  // - switch sa fie singur in metoda
  // - ai default throw

  // LEGE: n-ai voie cu Stringuri cu valori finite precunoscute
  //   -> enum la margine <=> iff daca faci logica ce depinde de el
  public double calculateCustomsTax(Parcel parcel) {
    TaxCalculator taxCalculator = selectTaxCalculator(parcel);
    return taxCalculator.calculateTax(parcel);
  }

  Map<Country, Class<? extends TaxCalculator>> countryToTaxCalculator = Map.of(
      Country.UK, UKTaxService.class,
//      Country.CN, ChinaTaxService.class,
      Country.FR, UETaxService.class,
      Country.ES, UETaxService.class,
      Country.RO, UETaxService.class
  );

//  private static final Map<Country, Function<Parcel, Double>> taxFunctions = Map.of(
//      Country.UK, p -> new UKTaxService().calculateTax(p),
//      Country.CN, p -> new ChinaTaxService().calculateTax(p),
//      Country.FR, p -> new UETaxService().calculateTax(p),
//      Country.ES, p -> new UETaxService().calculateTax(p),
//      Country.RO, p -> new UETaxService().calculateTax(p)
//  );
  // STATIC FACTORY METHOD PATTERN
  private static TaxCalculator selectTaxCalculator(Parcel parcel) {
//    var r = switch (parcel.originCountry()) {
//      case UK -> new UKTaxService();
//      case CN -> new ChinaTaxService();
//      case FR, ES, RO -> new UETaxService();
//      default -> throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
//    };
//    return p->0;

    return parcel.originCountry().calculator;
  }
}
// "STRATEGY" pattern
@FunctionalInterface
interface TaxCalculator {
  double calculateTax(Parcel parcel);
}
class UETaxService implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }
}
class ChinaTaxService implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() + parcel.regularValue() + 25;
  }
}
class UKTaxService implements TaxCalculator {
  public double calculateTax(Parcel parcel) {
    // un pic de cod in plus
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

