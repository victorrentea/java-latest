package victor.training.java.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringApp {
  public static void main(String[] args) {
    SpringApplication.run(SpringApp.class, args);
  }
}

//@RequiredArgsConstructor
//class MyService {
//  private final RecordCaDto oDep;

@Slf4j
@Service
record MyService(RecordCaDto oDep) {
  // in acest caz super simplu, merge record aici, dar in general NU merge record + @Service
//  @Transactional // orice adnotare pe metoda folosesti in spring,
    // va cauza frameworkul sa subclaseze clasa ta pt a genera un proxy.
  public void method() {

  }
}




@RestController
class RecordCaDto {

  @GetMapping("get")
  public MyDto get() {
    return new MyDto(1);
  }
  @PostMapping("post")
  public MyDto post(@Validated @RequestBody MyDto dto) {
    return dto;
  }
}

record MyDto(@Min(0) @NotNull Integer x) {}