package victor.training.java.patterns.strategy;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


class Strategy {
  public static void main(String[] args) {
    CustomsService service = new CustomsService();
    Parcel ro = new Parcel("RO", 100, 100, LocalDate.now());
    System.out.println("Tax for " + ro + " = " + service.calculateCustomsTax(ro));
    Parcel cn = new Parcel("CN", 100, 100, LocalDate.now());
    System.out.println("Tax for " + cn + " = " + service.calculateCustomsTax(cn));
    Parcel uk = new Parcel("UK", 100, 100, LocalDate.now());
    System.out.println("Tax for " + uk + " = " + service.calculateCustomsTax(uk));
  }
}

record Parcel(
    String originCountry,
    double tobaccoValue,
    double regularValue,
    LocalDate date) {
}

@Service
@Data
class CustomsService {
  public double calculateCustomsTax(Parcel parcel) {
    TaxCalculator taxCalculator;
    switch (parcel.originCountry()) {
      case "UK":
        taxCalculator = new UKTaxService();
        break;
      case "CN":
        taxCalculator = new ChinaTaxService();
        break;
      case "FR":
      case "ES":
      case "RO":
        taxCalculator = new UETaxService();
        break;
      default:
        throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
    }
    return taxCalculator.calculateTax(parcel);
  }
}
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

