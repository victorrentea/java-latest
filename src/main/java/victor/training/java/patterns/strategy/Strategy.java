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
    switch (parcel.originCountry()) {
      case "UK":
        return UKTaxService.calculateBrexitTax(parcel);
      case "CN":
        return ChinaTaxService.calculateChinaTax(parcel);
      case "FR":
      case "ES":
      case "RO":
        return UETaxService.calculateUETax(parcel);
      default:
        throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
    }
  }
}
class UETaxService {
  public static double calculateUETax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }
}
class ChinaTaxService {
  public static double calculateChinaTax(Parcel parcel) {
    return parcel.tobaccoValue() + parcel.regularValue() + 25;
  }
}
class UKTaxService {
  public static double calculateBrexitTax(Parcel parcel) {
    // un pic de cod in plus
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

