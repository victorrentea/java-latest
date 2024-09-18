package victor.training.java.virtualthread.experiments;

import me.escoffier.loom.loomunit.LoomUnitExtension;
import me.escoffier.loom.loomunit.ShouldNotPin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.Executors;

@ExtendWith(LoomUnitExtension.class)
public class ExperimentTest {
  @Test
  @ShouldNotPin // assert JFR Events
  void experiment() throws Exception {
    Experiment.main();
  }
}
