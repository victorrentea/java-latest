package victor.training.java.patterns.proxy;

public class RetryFP {
  public static void main(String[] args) {
    retry(3, RetryFP::riskyNetworkCall);
  }

//  @Retry(maxRetries = 3) from resilience4j
  public static void riskyNetworkCall() {
    if (Math.random() < 0.8) {
      throw new RuntimeException("Network error");
    }
    System.out.println("SUCCESS");
  }

  public static void retry(int maxRetries, Runnable codeToRetry) {
    for (int i = 0; i < maxRetries; i++) {
      try {
        codeToRetry.run();
        return;
      } catch (Exception e) {
        System.out.println("Retrying...");
      }
    }
    throw new RuntimeException("Failed after " + maxRetries + " retries");
  }

}
