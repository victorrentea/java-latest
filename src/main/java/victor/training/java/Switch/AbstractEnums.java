//package victor.training.java.Switch;
//
//class AbstractEnums {
//  enum CountryEnum {
//    RO {
//      @Override
//      double computeTax(Parcel parcel) {
//        return 0;
//      }
//    }, ES, FR, UK, CN;
//    abstract double computeTax(Parcel parcel);
//  }
//  record Parcel(CountryEnum originCountry, double tobaccoValue, double regularValue) {
//  }
//  public static void main(String[] args) {
//    System.out.println(computeTax(new Parcel(CountryEnum.RO, 100, 100)));
//    System.out.println(computeTax(new Parcel(CountryEnum.CN, 100, 100)));
//    System.out.println(computeTax(new Parcel(CountryEnum.UK, 100, 100)));
//  }
//
//  public static double computeTax(Parcel parcel) {
//    return parcel.originCountry().computeTax(parcel);
//  }
//
//}
//
