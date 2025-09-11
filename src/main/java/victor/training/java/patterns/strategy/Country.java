package victor.training.java.patterns.strategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Country {
  UK(new UKTaxService()),
//  CN, FR, ES, RO, CHAD;
  CN(new ChinaTaxService()),
  FR(new UETaxService()),
  ES(new UETaxService()),
  RO(new UETaxService()),
  CHAD(new UETaxService());

  public final TaxCalculator calculator;
}
