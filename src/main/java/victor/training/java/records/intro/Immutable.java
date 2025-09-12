package victor.training.java.records.intro;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// shallow immutable
@Builder(toBuilder = true) // De evitat. = regret ca ob asta e immutabil. vreau inapoi la mama (mutabil)
public record Immutable(
    String name,


    @With
    float lat,
    @With
    float lon,

    Other other,
    // ImmutableList:
    // DA pe Mongo, Dto, Redis
    // NU pe JPA
    ImmutableList<Integer> list) {

  //  public Immutable move(float newLat, float newLong) {}
  public Immutable translate(float deltaLat, float deltaLong) {
    return new Immutable(name, lat + deltaLat, lon + deltaLong, other, list);
  }

//  public Immutable(String name, Other other, List<Integer> list) {
//    this.list = List.copyOf(list);
//    this.name = name;
//    this.other = other;
//  }

//  @Override
//  public List<Integer> list() {
////    return new ArrayList<>(list); // NICIODATA nu a fost o idee buna pt ca malloc + misleading
////    return Collections.unmodifiableList(list); // *static factory method* care-ti intoarce un *Decorator*
////    return List.copyOf(list);  // mai sexy, dar tot prost ca malloc si ea
//    return list;
//  }
}