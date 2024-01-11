package victor.training.java.records;

import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import victor.training.java.records.RecordsInSpring.CreateBookRequest;

import java.util.List;

@SpringBootApplication
@RestController
public class RecordsInSpring {
  @Autowired
  private BookService bookService;

  public static void main(String[] args) {
    SpringApplication.run(RecordsInSpring.class, args);
  }

  public record GetBookResponse(long id, String name){}

  @GetMapping
  public GetBookResponse getBook() {
    return new GetBookResponse(1, "DDD");
  }

  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty List<String> authors,
      String teaserVideoUrl //
  ) {
  }

  @PostMapping
  public void createBook(@RequestBody @Validated CreateBookRequest request) {
    bookService.create(request);
  }
}

@Service
//record MyService(MyRepo myRepo) { // 🛑DON'T! => CGLIB won't be able to generate a proxy (dynamic subclass) of a final class
@RequiredArgsConstructor
class BookService {
  private final MyRepo myRepo;

  @Transactional
  public void create(CreateBookRequest request) {
    System.out.println("save title:" + request.title() + " and url:" + request.teaserVideoUrl());
    foo(request);
    System.out.println("save authors: " + request.authors());
  }

  private void foo(CreateBookRequest request) {
    request.authors().clear();
  }
}

@Repository
class MyRepo {
}
