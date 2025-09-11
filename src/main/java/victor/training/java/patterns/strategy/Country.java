package victor.training.java.patterns.strategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Country {
  UK(UKTaxService.class) {
    @Override
    public double calculateTax(Parcel parcel) {
      return 0; // logica de business in ultimul loc in care te-asteptai s-o gasesti.
    }
  },
//  CN, FR, ES, RO, CHAD;
  CN(ChinaTaxService.class),
  FR(UETaxService.class),
  ES(UETaxService.class),
  RO(UETaxService.class),
  CHAD(UETaxService.class);

  public final Class<? extends TaxCalculator> calculatorClass;

  public abstract double calculateTax(Parcel parcel);
}
