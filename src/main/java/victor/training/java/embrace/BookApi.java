package victor.training.java.embrace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//record BookApi(BookRepo bookRepo) { // ❌DON'T: proxies don't work on final classes => eg @Secured/@Transactional.. won't work
@RequiredArgsConstructor
public class BookApi {
  private final BookRepo bookRepo;

  // ✅ DTO
  public record CreateBookRequest(
      @NotBlank String title,
      @NotEmpty List<String> authors,
      String teaserVideoUrl // may be absent
  ) {
  }

  @PostMapping("books")
  @Transactional
  public void createBook(@RequestBody @Validated CreateBookRequest request) {
    System.out.println("title:" + request.title());
    System.out.println("teaser:" + request.teaserVideoUrl().toLowerCase()); // NPE
  }

  // ----

  public record SearchBookResult(
      long id,
      String name
  ) {
  }

  @GetMapping("books")
  public List<SearchBookResult> search(@RequestParam String title) {
    return bookRepo.search(title);
  }

}


