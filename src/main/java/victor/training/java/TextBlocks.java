package victor.training.java;

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
      @Query(value = """
          select t.id
              from TEACHER t
              where (?1 is null or upper(t.name) like upper(('%'||?1||'%')))
              and (?2 is null or t.grade=?2)
              and (cast(?3 as integer)=0 or exists
                   select 1
                   from TEACHING_ACTIVITY ta
                   inner join TEACHING_ACTIVITY_TEACHER tat on ta.id=tat.activities_id
                   inner join TEACHER tt on tat.teachers_id=tt.id
                   where ta.discr='COURSE'
                   and tt.id=t.id))
          """, nativeQuery = true)
      List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
   }


   // ## 2 no need to escape " or \n
   MockMvc mockMvc;

   void search(String name) throws Exception {
      // the next line tells IntelliJ to suggest editing the string as a JSON fragment
      // language=json
      String jsonTemplate = """
          {
             "name": "%s",
             "teachingCourses": true
          }
          """;
      String json = jsonTemplate.formatted(name);
      mockMvc.perform(post("/product/search")
              .contentType("application/json")
              .content(json) // add one more criteria
          )
          .andExpect(status().isOk()) // 200
          .andExpect(jsonPath("$", hasSize(1)));
   }

   static class TeachingActivity {}
}
