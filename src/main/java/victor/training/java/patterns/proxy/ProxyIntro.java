package victor.training.java.patterns.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProxyIntro {
  public static void main(String[] args) {
    // Play the role of Spring here (there's no framework)
    // TODO 1 : LOG the arguments of any invocation of a method in Maths w/ decorator
    // TODO 2 : without changing anything below the line (w/o any interface)
    // TODO 3 : so that any new methods in Maths are automatically logged [hard]
    Maths maths = new Maths();
    LoggingDecorator decorated = new LoggingDecorator(maths);
    SecondGrade secondGrade = new SecondGrade(decorated);
    new ProxyIntro().run(secondGrade);
    // TODO 4 : let Spring do its job, and do the same with an Aspect
//         SpringApplication.run(ProxyIntro.class, args);
  }
// REQUIREMENT: any method in Maths class should log its arguments
// without changing the Maths class.
// use OOP

  // =============== THE LINE =================
  @Autowired
  public void run(SecondGrade secondGrade) {
    System.out.println("At runtime...");
    secondGrade.mathClass();
  }
}
// REQUIREMENT: any method in Maths class should log its arguments
// without changing the Maths class. use OOP
@RequiredArgsConstructor
class LoggingDecorator extends Maths {
  private final Maths decorated;
  public int sum(int a, int b) {
    System.out.println("sum(" + a + "," + b + ")");
    return decorated.sum(a, b);
//    return super.sum(a, b);
  }
  public int product(int a, int b) {
    System.out.println("product(" + a + "," + b + ")");
    return decorated.product(a, b);
//    return super.sum(a, b);
  }
}

//  Maths maths = new Maths();
//    LoggingDecorator decorator = new LoggingDecorator(maths);
//    SecondGrade secondGrade = new SecondGrade(decorator);
@RequiredArgsConstructor
class SecondGrade {
  private final Maths maths;

  public void mathClass() {
    // all of these calls should be logged. this class is NOT aware that the sum/product methods
    // go to the LoggingDecorator instead of the Maths class, thanks to polymorphism
    System.out.println("2+4=" + maths.sum(2, 4));
    System.out.println("1+5=" + maths.sum(1, 5));
    System.out.println("2x3=" + maths.product(2, 3));
  }
}

class Maths {
  public int sum(int a, int b) {
    return a + b;
  }

  public int product(int a, int b) {
    return a * b;
  }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
