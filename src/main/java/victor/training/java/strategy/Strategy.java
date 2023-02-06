package victor.training.java.strategy;


import java.time.LocalDate;

class Strategy {
    public static void main(String[] args) {
        System.out.println("Tax for (RO,100,100) = " + calculateCustomsTax(
                new Parcel("RO", 100, 100, LocalDate.now())));
        System.out.println("Tax for (CN,100,100) = " + calculateCustomsTax(
                new Parcel("CN", 100, 100, LocalDate.now())));
        System.out.println("Tax for (UK,100,100) = " + calculateCustomsTax(
                new Parcel("UK", 100, 100, LocalDate.now())));
    }

    public static double calculateCustomsTax(Parcel parcel) { // UGLY API we CANNOT change
        switch (parcel.originCountry()) {
            case "UK":
                return parcel.tobaccoValue() / 2 + parcel.regularValue();
            case "CN":
                return parcel.tobaccoValue() + parcel.regularValue();
            case "FR":
            case "ES": // other EU country codes...
            case "RO":
                return parcel.tobaccoValue() / 3;
            default:
                throw new IllegalArgumentException("Not a valid country ISO2 code: " + parcel.originCountry());
        }
    }
}

