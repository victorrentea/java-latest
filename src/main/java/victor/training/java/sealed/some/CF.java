package victor.training.java.sealed.some;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CF {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    try {
      CompletableFuture.supplyAsync(() -> validate(args)) // fetch("http://")
          .exceptionally(e ->//    .catch(e=>)
          {
            System.out.println("Asa da: "+e);
            return null;// pentru ca.
          });


    } catch (Exception e) {
      //never happens
      System.out.println("NICIODATA");
    }

    CompletableFuture.supplyAsync(() -> validate(args)).get();
  }

  private static Object validate(String[] args) {
    throw new IllegalStateException("Vai vai");
  }
}
