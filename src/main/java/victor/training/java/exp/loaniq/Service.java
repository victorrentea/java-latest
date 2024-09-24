package victor.training.java.exp.loaniq;

import lombok.RequiredArgsConstructor;
import victor.training.java.exp.thick.Controller;

@RequiredArgsConstructor
public class Service {
  private final Hook hook;
//  Controller controller; // would not build as it causes a
  // dependency cycle between the two build artifacts
  public void method() {
    hook.showForm();
  }
}
