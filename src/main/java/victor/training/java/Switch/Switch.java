package victor.training.java.Switch;


import org.springframework.context.event.EventListener;
import victor.training.java.Switch.Parcel.CountryEnum;

import java.time.LocalDate;

import static victor.training.java.Switch.Parcel.CountryEnum.*;
import static victor.training.java.Switch.Parcel.CountryEnum.RO;


class Switch {
    public static void main(String[] args) {
        System.out.println("Tax for (RO,100,100) = " + calculateCustomsTax(
                new Parcel(RO, 100, 100, LocalDate.now())));
        System.out.println("Tax for (CN,100,100) = " + calculateCustomsTax(
                new Parcel(CN, 100, 100, LocalDate.now())));
        System.out.println("Tax for (UK,100,100) = " + calculateCustomsTax(
                new Parcel(UK, 100, 100, LocalDate.now())));
        System.out.println("Tax for (null,100,100) = " + calculateCustomsTax(
                new Parcel(null, 100, 100, LocalDate.now())));
    }

    // #### 1 - switch expression (enum) = exhaustive
    public static double calculateCustomsTax(Parcel parcel) { // UGLY API we CANNOT change
        double result = switch (parcel.originCountry()) {
            case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
            case CN -> parcel.tobaccoValue() + parcel.regularValue();
            case FR,ES,RO -> parcel.tobaccoValue() / 3;
            default->
                throw new IllegalStateException("Unexpected value: " + parcel.originCountry());
        };
        return result;
    }




    // #### 2 - switch expression non exhaustive
    public int switchNonExhaustive(String countryIsoCode, int parcelValue) {
        switch (countryIsoCode) {
            case "RO":
                return parcelValue * 2;
            case "FR":
                return parcelValue + 2;
            default:
                throw new IllegalArgumentException("Unknown country " + countryIsoCode);
        }
    }

    // #### 3 - enhanced switch statement (returning void)
    public void handleMessage(HRMessage message) {
        switch (message.type()) {
            case RAISE:
                handleRaiseSalary(message.content());
                break;
            case PROMOTE:
                handlePromote(message.content());
                break;
            case DISMISS:
                if (message.urgent())
                    handleDismissUrgent(message.content());
                else
                    handleDismiss(message.content());
                break;
            default:
                throw new IllegalArgumentException("Should never happen in prod: unknown message type" + message.type());
        }
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


    @EventListener
    public void method() {
    }

}

