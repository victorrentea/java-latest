package victor.training.java.virtualthread.util;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class Slide {
  private static Api api;

  public CompletableFuture<ABC> abc(String id) {
    return supplyAsync(() -> api.a(id))
        .thenCompose(a -> api.b(a)
            .thenCompose(b -> api.c(a, b)
                .thenApply(c -> new ABC(a, b, c))));
  }

  interface Api {
    String a(String id);

    CompletableFuture<String> b(String a);

    CompletableFuture<String> c(String a, String b);
  }

  public record ABC(String a, String b, String c) {
  }
}
