package victor.training.java.patterns.strategy;

public class SideEffectingSwitch {

  public record Message(Country country, String date){}

  //  @KafkaListener // Kafka Consumer
  public void consumerDubios(Message message) {
    Void _ = switch (message.country) {
      case UK -> ukLogic();
      case FR, ES, RO, CN -> other();
      case CHAD -> {
        System.out.println("ca nu compileaza fara");
        yield null;
      }
    };
  }

  public void consumerCuTeste(Message message){
    switch (message.country) {
      case UK -> ukLogic();
      case FR, ES, RO, CN  -> other();
      default -> throw new IllegalArgumentException("Not a valid country ISO2 code: " + message.country);
    }
  }

  private Void other() {
    System.out.println("RAISE ALARM");
    return null;
  }

  private Void ukLogic() {
    System.out.println("INSERT IN DB");
    return null;
  }
}
