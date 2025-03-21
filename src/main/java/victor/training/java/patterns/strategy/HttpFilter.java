package victor.training.java.patterns.strategy;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpFilter implements Filter {
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    System.out.println("Browser language: " + request.getHeader("Accept-Language"));

    chain.doFilter(req, res);

  }
}
