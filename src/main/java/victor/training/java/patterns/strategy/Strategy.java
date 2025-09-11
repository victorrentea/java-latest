package victor.training.java.patterns.strategy;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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

  // STATIC FACTORY METHOD PATTERN
  private static TaxCalculator selectTaxCalculator(Parcel parcel) {
    var r = switch (parcel.originCountry()) {
      case UK -> new UKTaxService();
      case CN -> new ChinaTaxService();
      case FR, ES, RO -> new UETaxService();
      //  NU PUNE default daca switch expression e pe enum
      default -> throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
    };
    return p->0;
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

