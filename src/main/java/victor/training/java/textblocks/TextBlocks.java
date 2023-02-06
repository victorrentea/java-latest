package victor.training.java.textblocks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TextBlocks {

   interface SomeSpringDataRepo extends JpaRepository<TeachingActivity, Long> {
      @Query("""
               SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE '1'='1' AND
               a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId)
               OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)
            """)
      List<TeachingActivity> complexQuery(long id1, long id2); // bogus
   }

   @Autowired
   MockMvc mockMvc;
   @Test
   void test() throws Exception {
      mockMvc.perform(post("/product/search")
              .contentType("application/json")
              .content("""       
                  {"name":"%s"}
                  """.formatted("nume")) // add one more criteria
          )
          .andExpect(status().isOk()) // 200
          .andExpect(jsonPath("$", hasSize(1)));
   }


   // TODO + test of Loan Pattern ->


//   private var s = "1"; // NU
//   public static void main(var args) { // NU
   public static void main(String[] args) {

//     var s; // NU "var"
     var s=
               """
               SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE '1'='1' AND
               a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId)
               OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)
               """;
      System.out.println(s);
   }
   static class TeachingActivity {}
}
