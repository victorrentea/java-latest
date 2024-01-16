package victor.training.java.records;

import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.ImmutableList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static victor.training.java.records.BookApi.GetBookResponse;

@RestController
@RequestMapping("books")
//record BookApi(BookRepo bookRepo) { // 🛑DON'T! Proxies don't work on final classes => AOP @Secured won't work
@RequiredArgsConstructor
class BookApi {
  private final BookRepo bookRepo;

  public record GetBookResponse(long id, String name) {
  }

  @GetMapping("{id}")
  public GetBookResponse getBook(Integer id) {
    return bookRepo.getBookById(id);
  }

  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty ImmutableList<String> authors,
      String teaserVideoUrl
  ) {
  }

  @PostMapping
  @Transactional
  public void createBook(@RequestBody @Validated CreateBookRequest request) {
    System.out.println("pretend save title:" + request.title() + " and url:" + request.teaserVideoUrl());
    System.out.println("pretend save authors: " + request.authors());
  }
}

@Entity // Hibernate, ORM, JPA
@Data // avoid using @Data on entities in real life
class Book {
  @Id
  @GeneratedValue
  private Integer id;
  private String title;
  @Embedded
  private BookAuthor author;
}

@Embeddable
record BookAuthor(
    String name,
    String email
) {
}

record BookDocument( // in Mongo
    /*@Id*/ Integer id,
                     String title
) {
}

interface BookRepo extends JpaRepository<Book, Integer> {
  @Query("select new victor.training.java.records.BookApi$GetBookResponse(b.id, b.title)\n" +
         "from Book b\n" +
         "where b.id = :id\n")
  GetBookResponse getBookById(Integer id);

  //region Legacy SQL
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
  // easy to copy-paste in your SQL editor 👆
  List<Integer> complexQuery(String namePart, Integer grade, boolean teachingCourses);
  //endregion
}
