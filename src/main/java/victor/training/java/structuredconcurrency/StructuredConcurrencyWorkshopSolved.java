package victor.training.java.structuredconcurrency;

import jdk.incubator.concurrent.StructuredTaskScope.ShutdownOnFailure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class StructuredConcurrencyWorkshopSolved extends StructuredConcurrencyWorkshop {
  public BookingOffersDto parallel() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      scope.join();

      return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
    }
  }

  public BookingOffersDto timeout() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        scope.joinUntil(Instant.now().plusMillis(500));
      } catch (TimeoutException e) {
        if (futureOffers.isDone()) {
          return new BookingOffersDto(futureOffers.resultNow(), "Probably Sunny");
        } else {
          throw new RuntimeException(e);
        }
      }
      return new BookingOffersDto(futureOffers.resultNow(), futureWeather.resultNow());
    }
  }

  public BookingOffersDto timelyOffers() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Future<List<String>> futureOffers1 = scope.fork(() -> apiClient.getBookingOffers(1));
      Future<List<String>> futureOffers2 = scope.fork(() -> apiClient.getBookingOffers(2));
      Future<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        scope.joinUntil(Instant.now().plusMillis(500));
      } catch (TimeoutException e) {
        List<String> allOffers = new ArrayList<>();
        if (futureOffers1.isDone()) {
          allOffers.addAll(futureOffers1.resultNow());
        }
        if (futureOffers2.isDone()) {
          allOffers.addAll(futureOffers2.resultNow());
        }
        if (allOffers.isEmpty()) throw new RuntimeException(e);
        return new BookingOffersDto(allOffers, "Probably Sunny");
      }
      return new BookingOffersDto(futureOffers1.resultNow(), futureWeather.resultNow());
    }
  }
}
