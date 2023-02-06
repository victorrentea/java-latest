package victor.training.java.structuredconcurrency;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

public class WorkshopSolvedTest extends StructuredConcurrencyWorkshopTest{
  @InjectMocks
  StructuredConcurrencyWorkshopSolved workshopSolved;

  @BeforeEach
  final void setSolved() {
    workshop = workshopSolved;
  }
}
