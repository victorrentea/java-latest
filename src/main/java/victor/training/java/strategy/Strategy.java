package victor.training.java.strategy;


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
        double valoare = switch (parcel.originCountry()) {
            case UK -> parcel.tobaccoValue() / 2 + parcel.regularValue();
            case CN -> parcel.tobaccoValue() + parcel.regularValue();
            case FR, ES, RO -> parcel.tobaccoValue() / 3;
            case UA -> 0;
            // 1) un caz viitor inca neimplementat <- devine antipattern in Java 17
                    // as prefera sa ma intrebe compilatorul: sefu - ce pun in switchul ala pentru val noua din enum?
            // 2) o gramada mare de case-uri care NU ar face nimic. 60-80% => ramane!

            // SOC: dupa 45 de ani de switch () {default} in java 17 va deveni antipattern sa pui default
            // cand vei vedea un default o sa te intrebi: cum fac sa-l sterg?
        };
        return valoare;
    }

    // #### 2 - switch expressions
    public int switchulAiciARENevoieDeDefaultChiarDacaIlFolCaExpresie(
            String countryIsoCode, int parcelValue) {
        // atunci cand nu poate valida javac ca ai acoperit toate cazurile (eg switch pe string)
        return switch (countryIsoCode) {
            case "RO" -> parcelValue * 2;
            case "FR" -> parcelValue + 2;
            default -> throw new IllegalArgumentException("Vezi ca nu stiu de tara asta " + countryIsoCode);
        };
    }

    // ## 3 - switch nu intoarce nimic (nu mai este expresie ci statement)

//    public void handleEvent(MyEvent) {
    public void handleMessageDePeMQ(Message message) {
        switch (message.type()) {
            case MARIRE -> handleMareste(message.content());
            case PROMOVARE -> handlePromoveaza(message.content());
            case CONCEDIERE -> handleConcediaza(message.content());
            default -> throw new IllegalArgumentException("NU ASTA IN PROD");
        };
//        Void vMereuNull = switch (message.type()) {
//            case MARIRE -> handleMareste(message.content());
//            case PROMOVARE -> handlePromoveaza(message.content());
//            case CONCEDIERE -> handleConcediaza(message.content());
//        };
        // java 11, risc sa uiti break
//        switch (message.type()) {
//            case MARIRE: handleMareste(message.content());break;
//            case PROMOVARE: handlePromoveaza(message.content());break;
//            case CONCEDIERE: handleConcediaza(message.content());break;
//        }
    }

    private Void handlePromoveaza(String content) {
        return null;
    }

    private Void handleConcediaza(String content) {
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
record Message(MessageType type, String content){}
