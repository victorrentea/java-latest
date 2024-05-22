package victor.training.java.embrace;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class Records {
  public static void main(String[] args) {
    SpringApplication.run(Records.class, args);
  }
}

@RestController
@RequiredArgsConstructor
//record BookApi(BookRepo bookRepo) { // 🛑DON'T: proxies don't work on final classes => eg @Secured/@Transactional.. won't work
class BookApi {
  private final BookRepo bookRepo;

  // DTO
  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty List<String> authors,
      String teaserVideoUrl // may be absent
  ) {
  }

  @PostMapping("books")
  @Transactional
  public void createBook(@RequestBody @Validated CreateBookRequest request) {
    System.out.println("pretend save title:" + request.title() + " and url:" + request.teaserVideoUrl());
    System.out.println("pretend save authors: " + request.authors());
  }

  // ----

  public record SearchBookResult(
      long id,
      String name
  ) {
  }

  @GetMapping("books")
  public List<SearchBookResult> search(@RequestParam String name) {
    return bookRepo.search(name);
  }

}

@Entity
@Data // avoid @Data on @Entity
class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;

  private String authorFirstName;
  private String authorLastName;
}


