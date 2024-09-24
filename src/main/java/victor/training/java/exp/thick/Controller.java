package victor.training.java.exp.thick;

import lombok.RequiredArgsConstructor;
import victor.training.java.exp.loaniq.Hook;
import victor.training.java.exp.loaniq.Service;

@RequiredArgsConstructor
public class Controller implements Hook {
  private final Service service;

  public void method() {
    service.method();
  }

  public void showForm() {
    System.out.println("Stuff to call from LoanIQ sometimes");
  }
}
