package victor.training.java.embrace;

import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult.Full;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // avoid @Data on @Entity
public class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String title;

//  private String authorFirstName;
//  private String authorLastName;
  @Embedded // DB does not chage
  private FullName author;
}
@Embeddable
record FullName(String firstName, String lastName) {}
