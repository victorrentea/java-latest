package victor.training.java.Switch;


import org.springframework.context.event.EventListener;
import victor.training.java.Switch.Parcel.CountryEnum;

import java.time.LocalDate;



class Switch {
    public static void main(String[] args) {
        System.out.println("Tax for (RO,100,100) = " + calculateCustomsTax(
                new Parcel(Parcel.CountryEnum.RO, 100, 100, LocalDate.now())));
        System.out.println("Tax for (CN,100,100) = " + calculateCustomsTax(
                new Parcel(Parcel.CountryEnum.CN, 100, 100, LocalDate.now())));
        System.out.println("Tax for (UK,100,100) = " + calculateCustomsTax(
                new Parcel(Parcel.CountryEnum.UK, 100, 100, LocalDate.now())));
        System.out.println("Tax for (null,100,100) = " + calculateCustomsTax(
                new Parcel(null, 100, 100, LocalDate.now())));
    }
    // Java 11 -> java.lang.NullPointerException: null
    // Java 17 -> java.lang.NullPointerException: Cannot invoke "victor.training.java.Switch.Parcel$CountryEnum.ordinal()" because "countryEnum" is null

    // #### 1 - switch expression (enum) = exhaustive
    public static double calculateCustomsTax(Parcel parcel) { // UGLY API we CANNOT change
        // in java 17 switch enhanced poate fi folosit expresie: da rezultat
        CountryEnum countryEnum = parcel.originCountry();
        return switch (countryEnum) {
            // daca countryEnum e null -> switch arunca NPE in orice vers de Java
            case null -> 10; // din java > 17 se poate case null ->
            case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
            case CN -> parcel.tobaccoValue() + parcel.regularValue();
            case FR, ES, RO, UA -> parcel.tobaccoValue() / 3;
//            default -> throw new IllegalArgumentException("valeu " + countryEnum); // mai rau asa ca-mi crapa abia in runtime
            // in java 17 daca folosesti switch (enum) ca o expresie (sa intoarca valoare),
            // e contraindicat sa pui default pentru crapa compilarea daca nu ai acoperit toate bransele
        };
        // java < 17 switch era doar statement.
//        switch (parcel.originCountry()) {
//            case UK:
//                return parcel.tobaccoValue() / 2 + parcel.regularValue();
//            case CN:
//                return parcel.tobaccoValue() + parcel.regularValue();
//            case FR:
//            case ES:
//            case RO:
//                return parcel.tobaccoValue() / 3;
//            default:
//                throw new IllegalStateException("Poate ca maine... Unexpected value, dar n-ar trebui sa se intample vreodata: " + parcel.originCountry());
//        }
    }

    // #### 2 - switch expression non exhaustive
    public int switchNonExhaustive(String countryIsoCode, int parcelValue) {
        return switch (countryIsoCode) {
            case "RO" -> parcelValue * 2;
            case "FR" -> parcelValue + 2;
            default -> throw new IllegalArgumentException("Unknown country " + countryIsoCode);
        };
    }

    // #### 3 - enhanced switch statement (returning void)
    public void handleMessage(HRMessage message) {
        // cum sa transform acest switch care doar face chestii intr-o expresie.
        // nu am nevoie de la el sa imi intoarca nimic, doar ca mi-ar placea sa-mi int0arca ceva sa folosec "return switch(enum)"
        Void degeaba = switch (message.type()) {
            case RAISE -> handleRaiseSalary(message.content());
            case PROMOTE-> handlePromote(message.content());
            case DISMISS-> {
                if (message.urgent()) {
                    yield handleDismissUrgent(message.content());
                }
                else
                    yield handleDismiss(message.content());
            }
//            default-> throw new IllegalArgumentException("JDD should never happen in prod");
            case HIRE -> null; //daca n-ai nimic de facut pe HIRE, mai bine pui asa decat
            // default->null;
        };
    }

    private Void handlePromote(String content) {
        return null;
    }

    private Void handleDismissUrgent(String content) {
        System.out.println(":( !!");
        return null;
    }
    private Void handleDismiss(String content) {
        System.out.println(":( !!");
        return null;
    }

    private Void handleRaiseSalary(String content) {
        System.out.println(":)");
        return null;
    }
    // Void mai poate fi gasit in:
    // - ResponseEntity<Void> in controller method (bad practice, ar trebui sa folosesti un @RestControllerAdvice + @ExceptionMapper
    // - CompletableFuture<Void> sau Mono<Void>
    // - aici


    @EventListener
    public void method() {
    }

}

