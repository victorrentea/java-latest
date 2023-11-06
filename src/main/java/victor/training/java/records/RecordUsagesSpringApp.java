package victor.training.java.records;

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

import java.util.List;

@SpringBootApplication
@RestController
public class RecordUsagesSpringApp {
  public static void main(String[] args) {
    SpringApplication.run(RecordUsagesSpringApp.class, args);
  }

  @Autowired
  private MyService service;

  // DTO flavors: Request/Response/Criteria/Result
  public record GetBookResponse(long id, String name){}
  @GetMapping
  public GetBookResponse getBook() {
    return new GetBookResponse(1, "DDD");
  }

  public record CreateBookRequest(
      @NotBlank String name,
      @NotEmpty List<String> authors
  ) {  }
  @PostMapping
  public long createBook(@RequestBody @Validated CreateBookRequest request) {
    return 1;
  }
}


@Service
@RequiredArgsConstructor
//record MyService(MyRepo myRepo) { // 🛑DON'T! => CGLIB won't be able to generate a proxy (dynamic subclass) of a final class
class MyService {
  private final MyRepo myRepo;

  @Transactional
  public void method() {

  }
}

@Repository
class MyRepo {
}
