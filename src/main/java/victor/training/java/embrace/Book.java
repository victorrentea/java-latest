package victor.training.java.embrace;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data // avoid @Data on @Entity
public class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;

  private String authorFirstName;
  private String authorLastName;
}
