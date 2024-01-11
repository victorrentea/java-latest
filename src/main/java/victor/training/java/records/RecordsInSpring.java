package victor.training.java.records;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static victor.training.java.records.BookController.*;

@SpringBootApplication
public class RecordsInSpring {
  public static void main(String[] args) {
    SpringApplication.run(RecordsInSpring.class, args);
  }
}

@RestController
//record BookController(BookRepo bookRepo) { // 🛑DON'T! Proxies don't work on final classes => AOP @Secured won't work
@RequiredArgsConstructor
class BookController {
  private final BookRepo bookRepo;

  public record GetBookResponse(long id, String name){}

  @GetMapping("{id}")
  public GetBookResponse getBook(Long id) {
    return bookRepo.getBookById(id);
  }

  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty List<String> authors,
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

@Entity
@Data // avoid using @Data on entities in real life
class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
}

interface BookRepo extends JpaRepository<Book, Long> {
  @Query("select new victor.training.java.records.BookController$GetBookResponse(b.id, b.title) " +
         "from Book b where b.id = :id")
  GetBookResponse getBookById(Long id);
}
