package victor.training.java.structuredconcurrency;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

public class WorkshopSolvedTest extends WorkshopTest {
  @InjectMocks
  WorkshopSolved workshopSolved;

  @BeforeEach
  final void setSolved() {
    workshop = workshopSolved;
  }
}
