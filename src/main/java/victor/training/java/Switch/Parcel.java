package victor.training.java.Switch;

import java.time.LocalDate;

public record Parcel(CountryEnum originCountry,
                     double tobaccoValue,
                     double regularValue,
                     LocalDate date) {

  public enum CountryEnum {
    RO/*{
      @Override
      double computeTax(Parcel parcel) {
        return 0;
      }
    }*/,
    ES,
    FR,
    UK,
    CN
//    UNKNOWN?
//    ,DB;
//    abstract double computeTax(Parcel parcel);
  }
}