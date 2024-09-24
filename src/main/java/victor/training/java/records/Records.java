package victor.training.java.records;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Records {
  public static void main(String[] args) {
    SpringApplication.run(Records.class, args);
  }
}

@Slf4j
@RestController

//record BookApi(BookRepo bookRepo) { // 🛑DON'T: proxies don't work on final classes => eg @Secured/@Transactional.. won't work
@RequiredArgsConstructor
class BookApi {
  private final BookRepo bookRepo; // this injects the bean named "bookRepo" from the Spring context
//  private final BookRepo differentName; // this injects the bean named "differentName" from the Spring context

  // DTO = Data Transfer Object (no logic, just data) moving across network
  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty List<String> authors,
      Optional<String> teaserVideoUrl // may be absent
  ) {
  }

  @PostMapping("books")
  @Transactional
  // @Validated  calls a proxy that validates the request parameters automatically
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

@Embeddable // in recent Hibernate versions, @Embeddable can be records
record Author(
    String firstName,
    String lastName
) {
}
@Entity // Hibernate/JPA
@Data // avoid @Data on @Entity
//record Book { // JPA mindset requires mutable entities. Records are immutable. So we can't use records for JPA entities
class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;

//  private String authorFirstName;
//  private String authorLastName;
  @Embedded
  private Author author; // grouped 2 fields into a single object
  // !!!!! WITHOUT changing the DB schema. (No ALTER TABLE needed)

  // + 54 more fields
}



