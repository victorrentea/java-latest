package victor.training.java.patterns.strategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Country {
  UK(UKTaxService.class),
//  CN, FR, ES, RO, CHAD;
  CN(ChinaTaxService.class),
  FR(UETaxService.class),
  ES(UETaxService.class),
  RO(UETaxService.class),
  CHAD(UETaxService.class);

  public final Class<? extends TaxCalculator> calculatorClass;


}
