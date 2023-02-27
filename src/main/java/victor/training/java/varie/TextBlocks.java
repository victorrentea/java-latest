package victor.training.java.varie;

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

   // ## 1 copy-paste from SQL editor :)
   interface SomeSpringDataRepo extends JpaRepository<TeachingActivity, Long> {
      @Query(nativeQuery = true, value = "    select t.id\n" +
                                         "    from TEACHER t\n" +
                                         "    where (?1 is null or upper(t.name) like upper(('%'||?1||'%')))\n" +
                                         "    and (?2 is null or t.grade=?2)\n" +
                                         "    and (cast(?3 as integer)=0 or exists\n" +
                                         "         select 1\n" +
                                         "         from TEACHING_ACTIVITY ta\n" +
                                         "         inner join TEACHING_ACTIVITY_TEACHER tat on ta.id=tat.activities_id\n" +
                                         "         inner join TEACHER tt on tat.teachers_id=tt.id\n" +
                                         "         where ta.discr='COURSE'\n" +
                                         "         and tt.id=t.id))\n")
      List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
   }


   // ## 2 no need to escape " or \n
   @Autowired
   MockMvc mockMvc;

   @Test
   void test() throws Exception {
      // language=json
      String json = ("{\n" +
                     "   \"name\": \"%s\",\n" +
                     "   \"teachingCourses\": true\n" +
                     "}\n").formatted("nume");
      mockMvc.perform(post("/product/search")
              .contentType("application/json")
              .content(json) // add one more criteria
          )
          .andExpect(status().isOk()) // 200
          .andExpect(jsonPath("$", hasSize(1)));
   }

   static class TeachingActivity {}
}
