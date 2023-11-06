package victor.training.java.records.intro;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

 //@Data // hash/eq/tostr, getter, setter
//@Value // hash/eq/tostr, getter, toate campurile 'private final' + construct pt ele
//public class Immutable {
//  String name; // ✅ immutable
//  Other other; // ✅ immutable
//  List<Integer> list; // ❌ mutable
//}
// sau..
public record Immutable(
     String name, // ✅ immutable
     Other other,  // ✅ immutable
     List<Integer> list // ❌ mutable

     // sau inca din java8 puteai renunta la Java Collection Framework,
     // si sa folosesti Guava (Google Java Commons) in schimb:
     // ImmutableList<Integer> list // ✅ immutable
) {

  public Immutable {
    list = List.copyOf(list); // copie imutabila❤️ a listei originale, java11
  }

//   public List<Integer> list() { // -malloc -misleading
//     return new ArrayList<>(list);
//   }
 }
// starea unui obiect imutabil NU poate fi modificata dupa instantiere.

// @Value sau record definesc 'shallow immutable' objects
// shallow = "superficial", putin adanc
// 🏆 deep immutable object = eu si toate obiectele pe care le refer sunt immutable