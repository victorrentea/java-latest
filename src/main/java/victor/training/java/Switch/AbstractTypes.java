package victor.training.java.Switch;

class AbstractTypes {
  enum CountryEnum {
    RO, ES, FR, UK, CN,
  }

  interface Country {
    double computeTax(Parcel parcel);
  }

  class ROCountry implements Country {
    @Override
    public double computeTax(Parcel parcel) {
      return 0;
    }
  }
  class ESCountry implements Country{
    @Override
    public double computeTax(Parcel parcel) {
      return 0;
    }
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

