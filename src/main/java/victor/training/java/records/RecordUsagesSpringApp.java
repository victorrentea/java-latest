package victor.training.java.records;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

@SpringBootApplication
@RestController
public class RecordUsagesSpringApp {
  public static void main(String[] args) {
    SpringApplication.run(RecordUsagesSpringApp.class, args);
  }

  @Autowired
  private MyService service;

  @GetMapping
  public MyDto get() {
    return new MyDto(1);
  }

  @PostMapping
  public MyDto post(@Validated @RequestBody MyDto dto) {
    return dto;
  }

  record MyDto(@Min(0) @NotNull Integer x) {
  }
}


@Service
@RequiredArgsConstructor
//record MyService(MyRepo myRepo) { // 🛑DON'T! => CGLIB cannot generate a proxy (dynamic subclass) of a final class
class MyService {
  private final MyRepo myRepo;

  @Transactional
  public void method() {

  }
}

@Repository
class MyRepo {
}
