package victor.training.java.patterns.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;
@SpringBootApplication
public class ProxyIntro {
  public static void main(String[] args) throws FileNotFoundException {
    // Play the role of Spring here (there's no framework)
    // TODO 1 : LOG the arguments of any invocation of a method in Maths w/ decorator
    // TODO 2 : without changing anything below the line (w/o any interface)
    // TODO 3 : so that any new methods in Maths are automatically logged [hard]
    Maths real = new Maths(); // instanta curata din beanul tau @Service (eg)
//    Maths decorata = new MathsCuMetrica(new MathsCuLogging(real));

    Callback h = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        long start = currentTimeMillis();

        Object r = method.invoke(real, args);

        long end = currentTimeMillis();
        System.out.println(method.getName() + " called with " + Arrays.toString(args) + " returning " + r + " took " + (end - start) + " ms");
        return r;
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, h);
    // CGLIB genereaza inmem bytecodeul unei subclasa la clasa ta.

    SecondGrade secondGrade = new SecondGrade(proxy);
//    new ProxyIntro().run();
    SpringApplication.run(ProxyIntro.class, args);
  }

  // =============== THE LINE =================

  @Autowired
  SecondGrade secondGrade;

  @EventListener(ApplicationStartedEvent.class)
  public void run() {

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
class Util {
  public static <T> T log(Supplier<T> supplier) {
    long t0 = currentTimeMillis();
    T r = supplier.get();
    long t1 = currentTimeMillis();
    System.out.println("returned " + r + " took " + (t1 - t0) + " ms");
    return r;
  }
}
@Service
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    System.out.println("ce-mi injecteaza aici? "+ maths.getClass());
    this.maths = maths;
  }

  public void mathClass() {
    // la call-site
    System.out.println("2+4=" + Util.log(() -> maths.sum(2, 4)));
    System.out.println("1+5=" + maths.sum(1, 5));
    System.out.println("2x3=" + maths.product(2, 3));
  }
}
@Service
/*🚫final */class Maths {
//  @Timed()
//  @Secured("ROLE_ADMIN")
//  @PreAuthorize("
//  @Transactional
//  @Cacheable

  // la declaration site.
  @LoggedMethod
  public /*🚫static*/ int sum(int a, int b) {
    return a + b;
  }
  public /*🚫final*/ int product(int a, int b) {
    int total = 0;
    for (int i = 0; i < a; i++) {
      total = sum(total, b); // 👑🚫 Principala tzeapa: adnotarile nu au efect cand chemi metoda in aceeasi clasa!!
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
