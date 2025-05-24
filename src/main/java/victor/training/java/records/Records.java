package victor.training.java.records;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
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

  // API DTO, obiecte volatile, persistente {Mongo,Redis..}\ {JPA}
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

//  @Builder pt orice record > 5 campuri
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

  @Embeddable
  public record AuthorName(
      // manevra asta nu necesita ALTER TABLE. e doar de mapare de Hib
      String firstName,
      String lastName
  ) {}
  // in tabela au ramas urmatoarele coloane:
  // author_first_name, author_last_name, id, title

  @Embedded
  private AuthorName authorName;
//  private String authorFirstName;
//  private String authorLastName;
}


