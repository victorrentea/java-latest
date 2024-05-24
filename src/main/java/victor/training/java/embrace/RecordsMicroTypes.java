package victor.training.java.embrace;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class RecordsMicroTypes {

   //region source of data
   public Map<CustomerId, Set<ProductCount>> extremeFP() {
      return Map.of(new CustomerId(1L), Set.of(
          new ProductCount("Table", 2),
          new ProductCount("Chair", 4)
      ));
   }
   //endregion

   record ProductCount(String name, int count) {}
   record CustomerId(long id) {} // hope Valhalla

   void lackOfAbstractions() {
      Map<CustomerId, Set<ProductCount>> map = extremeFP();
      // 🚫Don't use 'var' above

      for (CustomerId cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.count() + " of " + t.name())
             .collect(joining(", "));
         System.out.println("cid=" + cid + " got " + pl);
      }
   }

   void printCart(long cid, String c) {
      // etc
   }

}
