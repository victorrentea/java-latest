package victor.training.java.patterns.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import static java.lang.System.currentTimeMillis;

public class ProxyIntro {
  public static void main(String[] args) {
    // Play the role of Spring here (there's no framework)
    // TODO 1 : LOG the arguments of any invocation of a method in Maths w/ decorator
    // TODO 2 : without changing anything below the line (w/o any interface)
    // TODO 3 : so that any new methods in Maths are automatically logged [hard]
    Maths real = new Maths();
    Maths decorata = new MathsCuMetrica(new MathsCuLogging(real));
    SecondGrade secondGrade = new SecondGrade(decorata);
    new ProxyIntro().run(secondGrade);
//    SpringApplication.run(ProxyIntro.class, args);
  }

  // =============== THE LINE =================

  public void run(SecondGrade secondGrade) {
    System.out.println("At runtime...");
    secondGrade.mathClass();
  }
}
class MathsCuMetrica extends Maths {
  private final Maths maths;

  MathsCuMetrica(Maths maths) {
    this.maths = maths;
  }

  @Override
  public int sum(int a, int b) {
    long start = currentTimeMillis();
    int r = maths.sum(a, b);
    long end = currentTimeMillis();
    System.out.println("sum took " + (end - start) + " ms");
    return r;
  }
}
class MathsCuLogging extends Maths {
  private final Maths maths;

  MathsCuLogging(Maths maths) {
    this.maths = maths;
  }

  @Override
  public int sum(int a, int b) {
    int r = maths.sum(a, b);
    System.out.println("sum called with " + a + "," + b + " returning " + r);
    return r;
  }
}
// scriind cod doar deasupra liniei, afla ce-a facut fii-ta la mate azi.
// printeaza toate apelurile catre Maths pe care le-a facut SecondGrade, cu param si return value
// ==============
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    System.out.println("ce-mi injecteaza aici? "+ maths.getClass());
    this.maths = maths;
  }

  public void mathClass() {
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
    int total = 0;
    for (int i = 0; i < a; i++) {
      total = sum(total, b);
    }
    return total;
  }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
