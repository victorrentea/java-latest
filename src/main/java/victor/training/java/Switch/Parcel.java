package victor.training.java.Switch;

import java.time.LocalDate;

public record Parcel(CountryEnum originCountry,
                     double tobaccoValue,
                     double regularValue,
                     LocalDate date) {

  public enum CountryEnum {
    RO,
    ES,
    FR,
    UK,
    CN
//    ,DB
  }
}