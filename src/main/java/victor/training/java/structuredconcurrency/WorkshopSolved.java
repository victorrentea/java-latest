package victor.training.java.structuredconcurrency;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class WorkshopSolved extends Workshop {
  public BookingOffersDto p01_parallel() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      scope.join();

      return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
    }
  }

  public BookingOffersDto p02_timeout() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        Instant deadline = Instant.now().plusMillis(500);
        scope.joinUntil(deadline);
        return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
      } catch (TimeoutException e) {
        if (futureOffers.isDone()) {
          return new BookingOffersDto(futureOffers.resultNow(), "Probably Sunny");
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public BookingOffersDto p03_timelyOffers() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers1 = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<List<String>> futureOffers2 = scope.fork(() -> apiClient.getBookingOffers(2));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        Instant deadline = Instant.now().plusMillis(500);
        scope.joinUntil(deadline);

        // all 3 subtasks completed successfully
        List<String> allOffers = Stream.concat(futureOffers1.resultNow().stream(), futureOffers2.resultNow().stream()).toList();
        return new BookingOffersDto(allOffers, futureWeather.resultNow());
      } catch (TimeoutException e) {
        List<String> allOffers = new ArrayList<>();
        if (futureOffers1.isDone()) {
          allOffers.addAll(futureOffers1.resultNow());
        }
        if (futureOffers2.isDone()) {
          allOffers.addAll(futureOffers2.resultNow());
        }
        if (allOffers.isEmpty()) throw new RuntimeException("No offer received in time", e);

        String weather = futureWeather.isDone() ? futureWeather.resultNow() : "Probably Sunny";

        return new BookingOffersDto(allOffers, weather);
      }
    }
  }
}
