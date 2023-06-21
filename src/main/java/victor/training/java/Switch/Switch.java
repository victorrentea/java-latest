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
    switch (parcel.originCountry()) { // #3
      case UK:
        return parcel.tobaccoValue() / 2 + parcel.regularValue();
      case CN: // #1
        return parcel.tobaccoValue() + parcel.regularValue();
      case FR:
      case ES:
      case RO:
        return parcel.tobaccoValue() / 3;
      default: // #2
        // YOLO developer
        /// "i do not test my code, but when I do, I do it in PRODUCTION"
        throw new IllegalStateException("Unexpected value: " + parcel.originCountry());
    }
  }

}

