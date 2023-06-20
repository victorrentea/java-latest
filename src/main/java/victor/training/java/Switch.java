package victor.training.java;


import static victor.training.java.CountryEnum.*;


enum CountryEnum {
  RO, ES, FR, UK, CN,
}
record Parcel(CountryEnum originCountry, double tobaccoValue, double regularValue) {

}

class Switch {
  public static void main(String[] args) {
    System.out.println(computeTax(new Parcel(RO, 100, 100)));
    System.out.println(computeTax(new Parcel(CN, 100, 100)));
    System.out.println(computeTax(new Parcel(UK, 100, 100)));
  }

  public static double computeTax(Parcel parcel) { // UGLY API we CANNOT change
    double result = 0;
    switch (parcel.originCountry()) {
      case UK:
        result = parcel.tobaccoValue() / 2 + parcel.regularValue();
        break;
      case CN:
        result = parcel.tobaccoValue() + parcel.regularValue();
        break;
      case FR:
      case ES:
      case RO:
        result = parcel.tobaccoValue() / 3;
        break;
    }
    return result;
  }


}

