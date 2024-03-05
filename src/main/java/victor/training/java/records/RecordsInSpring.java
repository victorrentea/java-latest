package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static victor.training.java.records.BookApi.GetBookResponse;

@SpringBootApplication
public class RecordsInSpring {
  public static void main(String[] args) {
    SpringApplication.run(RecordsInSpring.class, args);
  }
}

@Slf4j
@RestController
@RequestMapping("books")
//record BookApi(BookRepo bookRepo) { // 🛑DON'T! Proxies don't work on final classes => AOP @Secured won't work
@RequiredArgsConstructor //
class BookApi {
  private final BookRepo bookRepo;

  public record GetBookResponse(long id, String name) {
  }

  @GetMapping("{id}")
  public GetBookResponse getBook(Long id) {
    return bookRepo.getBookById(id);
  }

  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty ImmutableList<String> authors, // guava
      Optional<String> teaserVideoUrl
  ) {
//    @Override
//    public String teaserVideoUrl() {
//      return teaserVideoUrl;
//    }
  }

  @PostMapping
  @Transactional
  public void createBook(@RequestBody @Validated CreateBookRequest request) {
    System.out.println("pretend save title:" + request.title() + " and url:" +
                       request.teaserVideoUrl().orElse("N/A"));
    dark(request);
    System.out.println("pretend save authors: " + request.authors());
  }

  private void dark(CreateBookRequest request) {
    request.authors().clear();
  }
}

@Entity
//@Data //BAD because: no encapsulation, no immutability, equals/hashCode wrongly implemented taking ID into account
@Getter
@Setter
class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
//  private String authorFirstName;
//  private String authorLastName;
  @Embedded private FullName author; // DB schema is not affected by this
}

@Embeddable
record FullName(String firstName, String lastName) {
}


interface BookRepo extends JpaRepository<Book, Long> {
  @Query("select new victor.training.java.records.BookApi$GetBookResponse(b.id, b.title)\n" +
         "from Book b\n" +
         "where b.id = :id")
  GetBookResponse getBookById(Long id);

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
  List<Long> complexQuery(String namePart, Integer grade, boolean teachingCourses);
  //endregion
}
