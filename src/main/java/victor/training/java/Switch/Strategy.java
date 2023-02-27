package victor.training.java.Switch;


import org.springframework.context.event.EventListener;

import java.time.LocalDate;



class Strategy {
    public static void main(String[] args) {
        System.out.println("Tax for (RO,100,100) = " + calculateCustomsTax(
                new Parcel(CountryEnum.RO, 100, 100, LocalDate.now())));
        System.out.println("Tax for (CN,100,100) = " + calculateCustomsTax(
                new Parcel(CountryEnum.CN, 100, 100, LocalDate.now())));
        System.out.println("Tax for (UK,100,100) = " + calculateCustomsTax(
                new Parcel(CountryEnum.UK, 100, 100, LocalDate.now())));
    }

    // #### 1 - switch expressions
    public static double calculateCustomsTax(Parcel parcel) { // UGLY API we CANNOT change
        double valoare = 0;
        switch (parcel.originCountry()) {
            case UK:
                valoare = parcel.tobaccoValue() / 2 + parcel.regularValue();
                break;
            case CN:
                valoare = parcel.tobaccoValue() + parcel.regularValue();
                break;
            case FR:
            case ES:
            case RO:
                valoare = parcel.tobaccoValue() / 3;
                break;
            case UA:
                valoare = 0;
                break;
        }
        return valoare;
    }

    // #### 2 - switch expressions
    public int switchOnNonExhaustiveCriteria(
            String countryIsoCode, int parcelValue) {
        // atunci cand nu poate valida javac ca ai acoperit toate cazurile (eg switch pe string)
        switch (countryIsoCode) {
            case "RO":
                return parcelValue * 2;
            case "FR":
                return parcelValue + 2;
            default:
                throw new IllegalArgumentException("Vezi ca nu stiu de tara asta " + countryIsoCode);
        }
    }

    // ## 3 - switch nu intoarce nimic (nu mai este expresie ci statement)

    public void handleMessage(Message message) {
        switch (message.type()) {
            case MARIRE:
                handleMareste(message.content());
                break;
            case PROMOVARE:
                handlePromoveaza(message.content());
                break;
            case CONCEDIERE:
                if (message.urgent())
                    handleConcediazaUrgent(message.content());
                else
                    handleConcediazaNormal(message.content());
                break;
            default:
                throw new IllegalArgumentException("NU ASTA IN PROD");
        }
    }

    private Void handlePromoveaza(String content) {
        return null;
    }

    private Void handleConcediazaUrgent(String content) {
        System.out.println(":( !!");
        return null;
    }
    private Void handleConcediazaNormal(String content) {
        System.out.println(":( !!");
        return null;
    }

    private Void handleMareste(String content) {
        System.out.println(":)");
        return null;
    }


    @EventListener
    public void method() {
    }

}

enum MessageType {
    CONCEDIERE,MARIRE,PROMOVARE, ANGAJARE
}
record Message(MessageType type, String content, boolean urgent){}
