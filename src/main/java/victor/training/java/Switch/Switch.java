package victor.training.java.Switch;

class Switch {
  enum CountryEnum {
    RO, ES, FR, UK, CN, US
  }
  record Parcel(CountryEnum originCountry, double tobaccoValue, double regularValue) {
  }
  public static void main(String[] args) {
    System.out.println(computeTax(new Parcel(CountryEnum.RO, 100, 100)));
    System.out.println(computeTax(new Parcel(CountryEnum.CN, 100, 100)));
    System.out.println(computeTax(new Parcel(CountryEnum.UK, 100, 100)));
  }

  public static double computeTax(Parcel parcel) {
    double valueOMG = switch (parcel.originCountry()) { // #3
      case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
      case CN -> // #1
          parcel.tobaccoValue() + parcel.regularValue();
      case FR, ES, RO -> parcel.tobaccoValue() / 3;
      case US -> 1;
    };
    return valueOMG;
  }

}

