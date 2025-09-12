package victor.training.java.patterns.strategy;

import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

enum UserField{
//  NAME("Name", User::getName);

}
public class MetRefs {
  public static void main(String[] args) {
//    new UETaxService().calculateTax(new Parcel());
    //are nevoie de "target typing"
    BiFunction<UETaxService, Parcel, Double> ref = UETaxService::calculateTax;
    CuOSinguraMetoda ref_targetTyping = UETaxService::calculateTax;

    UETaxService instantaExistenta = new UETaxService();
    Function<Parcel, Double> ref2= instantaExistenta::calculateTax;

    int i = "aa".length();

    Function<String, Integer> x = String::length;
    Supplier<Integer> oDoamne = ""::length;
    System.out.println(oDoamne.get());
    var d = new Date();
    Supplier<Date> ctor = Date::new;
  }

  interface CuOSinguraMetoda {
    Double f(UETaxService p1, Parcel p2);
  }
}
