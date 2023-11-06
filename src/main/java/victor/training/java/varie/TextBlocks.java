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
      @Query(nativeQuery = true, value = """
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
          """)
      List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
   }


   // ## 2 no need to escape " or \n
   @Autowired
   MockMvc mockMvc;

   @Test
   void test() throws Exception {
      // Surviving with text blocks:
      // 1: Alt-Enter: copy string concatenation..., edit in PostMan/VSC, paste inapoi in IJ intre ""
      // 2: Alt-Enter: Edit XXXXXX Fragment cu  // language=json inainte
      // 3: Text Blocks (java 17)

      // the next comment tells IntelliJ to suggest editing the string as a JSON fragment
      // language=json
      String jsonTemplate = "{\n" +
                            "   \"name\": \"%s\",\n" +
                            "   \"age\": 17,\n" +
                            "   \"teachingCourses\": true,\n" +
                            "   \"frate\": true\n" +
                            "}";
      String json = jsonTemplate.formatted("John");
      mockMvc.perform(post("/product/search")
              .contentType("application/json")
              .content(json) // add one more criteria
          )
          .andExpect(status().isOk()) // 200
          .andExpect(jsonPath("$", hasSize(1)));
   }

   public static void main(String[] args) {
      // dupa primele """ trebuie enter care insa nu apare in stringul final
      // language=json
      metoda(new Person("j".repeat(10), 37));
   }
   record Person(String name, int age) {}
   private static void metoda(Person person) {
      String json = STR."""
          {
             "name": "\{person.name}",
             "age": \{person.age},
             "a": 18,
             "teachingCourses": true,
             "frate": true
           }""";
      System.out.println("Nostalgia %2d %s %.2f ca-n C++".formatted(1, "a", 0.4));
      System.out.println("---");
      System.out.println(json);
      System.out.println("---");
   }

   static class TeachingActivity {}
}
