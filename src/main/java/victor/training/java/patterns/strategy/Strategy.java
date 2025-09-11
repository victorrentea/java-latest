package victor.training.java.patterns.strategy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
class Strategy {
  private final CustomsService service;

  Strategy(CustomsService service) {
    this.service = service;
  }

  public static void main(String[] args) {
    SpringApplication.run(Strategy.class, args);
  }


  @EventListener(ApplicationStartedEvent.class)
  public void run() {
    Parcel ro = new Parcel(Country.RO, 100, 100, LocalDate.now());
    System.out.println("Tax for " + ro + " = " + service.calculateCustomsTax(ro));
    Parcel cn = new Parcel(Country.CN, 100, 100, LocalDate.now());
    System.out.println("Tax for " + cn + " = " + service.calculateCustomsTax(cn));
    Parcel uk = new Parcel(Country.UK, 100, 100, LocalDate.now());
    System.out.println("Tax for " + uk + " = " + service.calculateCustomsTax(uk));
    Parcel chad = new Parcel(Country.CHAD, 100, 100, LocalDate.now());
    System.out.println("Tax for " + chad + " = " + service.calculateCustomsTax(chad));
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


  //  private static final Map<Country, Function<Parcel, Double>> taxFunctions = Map.of(
//      Country.UK, p -> new UKTaxService().calculateTax(p),
//      Country.CN, p -> new ChinaTaxService().calculateTax(p),
//      Country.FR, p -> new UETaxService().calculateTax(p),
//      Country.ES, p -> new UETaxService().calculateTax(p),
//      Country.RO, p -> new UETaxService().calculateTax(p)
//  );
  // STATIC FACTORY METHOD PATTERN
  private TaxCalculator selectTaxCalculator(Parcel parcel) {
    for (TaxCalculator taxCalculator : taxCalculators) {
      if (taxCalculator.isEligible(parcel))
        return taxCalculator;
    }
    throw new IllegalArgumentException("No tax calculator for country " + parcel.originCountry());
  }

  @Autowired
  List<TaxCalculator> taxCalculators; // toate Care Implementeaza
}

// "CHAIN OF RESPONSIBILITY" pattern
interface TaxCalculator {
  boolean isEligible(Parcel parcel);
  double calculateTax(Parcel parcel);
}

@Service
@Order(1)
class ChinaTaxService implements TaxCalculator {
  @Override
  public boolean isEligible(Parcel parcel) {
    return parcel.originCountry() == Country.CN;
  }

  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() + parcel.regularValue() + 25;
  }
}

@Service
@Order(9999)
class DefaultTaxService implements TaxCalculator {
  @Override
  public boolean isEligible(Parcel parcel) {
    return true;
  }
  @Override
  public double calculateTax(Parcel parcel) {
    return 25;
  }
}

@Service
@Order(1)
class UETaxService implements TaxCalculator {
  @Override
  public boolean isEligible(Parcel parcel) {
    return List.of(Country.FR, Country.ES, Country.RO)
        .contains(parcel.originCountry());
  }

  public double calculateTax(Parcel parcel) {
    return parcel.tobaccoValue() / 3;
  }
}

@Service
@Order(1)

class UKTaxService implements TaxCalculator {
  @Override
  public boolean isEligible(Parcel parcel) {
    return parcel.originCountry() == Country.UK;
  }

  public double calculateTax(Parcel parcel) {
    // un pic de cod in plus
    return parcel.tobaccoValue() / 2 + parcel.regularValue();
  }
}

