package victor.training.java.virtualthread.structuredconcurrency;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.util.concurrent.StructuredTaskScope.Subtask.State.SUCCESS;

public class WorkshopSolved extends Workshop {
  public BookingOffersDto p01_parallel() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Subtask<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      scope.join();

      return new BookingOffersDto(futureOffers.get(), futureWeather.get());
    }
  }

  public BookingOffersDto p02_timeout() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<List<String>> futureOffers = scope.fork(() -> apiClient.getBookingOffers(1));
      Subtask<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        Instant deadline = Instant.now().plusMillis(500);
        scope.joinUntil(deadline);
        return new BookingOffersDto(futureOffers.get(), futureWeather.get());
      } catch (TimeoutException e) {
        if (futureOffers.state() == Subtask.State.SUCCESS) {
          return new BookingOffersDto(futureOffers.get(), "Probably Sunny");
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public BookingOffersDto p03_timelyOffers() throws InterruptedException {
    try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
      Subtask<List<String>> futureOffers1 = scope.fork(() -> apiClient.getBookingOffers(1));
      Subtask<List<String>> futureOffers2 = scope.fork(() -> apiClient.getBookingOffers(2));
      Subtask<String> futureWeather = scope.fork(() -> apiClient.getWeather());

      try {
        Instant deadline = Instant.now().plusMillis(500);
        scope.joinUntil(deadline);

        // all 3 subtasks completed successfully
        List<String> allOffers = Stream.concat(futureOffers1.get().stream(), futureOffers2.get().stream()).toList();
        return new BookingOffersDto(allOffers, futureWeather.get());
      } catch (TimeoutException e) {
        List<String> allOffers = new ArrayList<>();
        if (futureOffers1.state()==SUCCESS) {
          allOffers.addAll(futureOffers1.get());
        }
        if (futureOffers2.state()==SUCCESS) {
          allOffers.addAll(futureOffers2.get());
        }
        if (allOffers.isEmpty()) throw new RuntimeException("No offer received in time", e);

        String weather = futureWeather.state()==SUCCESS ? futureWeather.get() : "Probably Sunny";

        return new BookingOffersDto(allOffers, weather);
      }
    }
  }
}
