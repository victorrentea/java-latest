package victor.training.java.parallelStream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static victor.training.java.Util.sleepMillis;

@Slf4j
public class ParallelStreams {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // OnAServer.otherParallelRequestsAreRunning(); // starve the shared commonPool din JVM

    List<Integer> productIds = IntStream.range(1, 100).boxed().toList();

    long t0 = System.currentTimeMillis();

    var result = productIds.parallelStream()
        .filter(i -> i % 2 == 0)
        .map(id -> fetchProduct(id))
        .toList();
    // increased speed by 10x.
    //
    //the improve is amazing.
    //But: what kind of work do you want to parallelize?
    // parallelStreams were designed for:
    // - ✅CPU-bound: eg: image processing, video encoding, encryption, decryption, XSLT
    //    spread the work to all available CPU cores
    // - 🚫IO-bound: eg: network calls, DB calls, REST calls, SOAP calls
    //    > #1 issue: thread starvation: you will abuse the JVM commonPool.
    //       see this video https://www.youtube.com/watch?v=0hQvWIdwnw4
    //       to see how to run your parallel stream in a custom pool

    //    > #2 issue: if you go over the network to the same server
          // famous remote-call-in-a-loop antipattern
              // GET products/1
              // GET products/7
              // GET products/9
              // GET products/10
    //     It would be much smarter if instead of fetching products ONE BY ONE
    //     you would fetch them in pages (eg: 1000 products at a time)
    // example :  GET products?ids=1,7,9,10


    long t1 = System.currentTimeMillis();
    log.info("Took {} ms to get: {}", t1 - t0, result);
  }

  private static int fetchProduct(Integer i) {
    log.info("Map " + i);
    sleepMillis(100); // network call (DB, REST, SOAP..) or CPU work
    return i * 2;
  }
}
