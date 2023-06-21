package victor.training.java.Switch;

class AbstractEnums {
  enum CountryEnum {
    RO, ES, FR, UK, CN,
  }
  record Parcel(CountryEnum originCountry, double tobaccoValue, double regularValue) {
  }
  public static void main(String[] args) {
    System.out.println(computeTax(new Parcel(CountryEnum.RO, 100, 100)));
    System.out.println(computeTax(new Parcel(CountryEnum.CN, 100, 100)));
    System.out.println(computeTax(new Parcel(CountryEnum.UK, 100, 100)));
  }

  public static double computeTax(Parcel parcel) {
    double tax = 0;
    switch (parcel.originCountry()) {
      case UK:
        tax = parcel.tobaccoValue() / 2 + parcel.regularValue();
        break;
      case CN:
        tax = parcel.tobaccoValue() + parcel.regularValue();
        break;
      case FR:
      case ES:
      case RO:
        tax = parcel.tobaccoValue() / 3;
        break;
    }
    return tax;
  }

}
