package victor.training.java.virtualthread.structuredconcurrency;

import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.virtualthread.structuredconcurrency.Workshop;
import victor.training.java.virtualthread.structuredconcurrency.Workshop.ApiClient;
import victor.training.java.virtualthread.structuredconcurrency.Workshop.BookingOffersDto;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

// Note: if you get the exception java.lang.NoClassDefFoundError: jdk/incubator/concurrent/StructuredTaskScope$ShutdownOnFailure
// you need to add '--add-modules jdk.incubator.concurrent' to the launch profile of the tests
@TestMethodOrder(MethodName.class)
@ExtendWith(MockitoExtension.class)
class WorkshopTest {
  @Mock
  ApiClient apiClient;
  @InjectMocks
  Workshop workshop;

  @Test
  @Timeout(value = 150, unit = MILLISECONDS)
  void p01_parallel() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(100, new Returns("Rain")));

    BookingOffersDto r = workshop.p01_parallel();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Rain"));
  }

  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p02_timeoutOnTime() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(100, new Returns("Rain")));

    BookingOffersDto r = workshop.p02_timeout();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Rain"));
  }

  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p02_timeoutTimeoutsWeather_throws() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(700, new Returns("Rain")));

    BookingOffersDto r = workshop.p02_timeout();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Probably Sunny"));
  }

  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p02_timeoutTimeoutsOffers_defaultsWeather() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(600, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(200, new Returns("Rain")));

    assertThatThrownBy(() -> workshop.p02_timeout())
            .hasCauseInstanceOf(TimeoutException.class);
  }

  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p03_timelyOffers_onTime() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer1"))));
    when(apiClient.getBookingOffers(2)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer2"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(300, new Returns("Rain")));

    BookingOffersDto r = workshop.p03_timelyOffers();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer1","offer2"), "Rain"));
  }

  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p03_timelyOffers_defaultsWeather() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer1"))));
    when(apiClient.getBookingOffers(2)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer2"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(900, new Returns("Rain")));

    BookingOffersDto r = workshop.p03_timelyOffers();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer1","offer2"), "Probably Sunny"));
  }
  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p03_timelyOffers_servesOffer1() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer1"))));
    when(apiClient.getBookingOffers(2)).thenAnswer(new AnswersWithDelay(1400, new Returns(List.of("offer2"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(300, new Returns("Rain")));

    BookingOffersDto r = workshop.p03_timelyOffers();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer1"), "Rain"));
  }
  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p03_timelyOffers_servesOffer2() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(1400, new Returns(List.of("offer1"))));
    when(apiClient.getBookingOffers(2)).thenAnswer(new AnswersWithDelay(400, new Returns(List.of("offer2"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(300, new Returns("Rain")));

    BookingOffersDto r = workshop.p03_timelyOffers();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer2"), "Rain"));
  }
  @Test
  @Timeout(value = 600, unit = MILLISECONDS)
  void p03_timelyOffers_throws_whenNoOffersFound() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(1400, new Returns(List.of("offer1"))));
    when(apiClient.getBookingOffers(2)).thenAnswer(new AnswersWithDelay(1400, new Returns(List.of("offer2"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(300, new Returns("Rain")));

    assertThatThrownBy(() -> workshop.p03_timelyOffers())
            .hasMessageContaining("No offer received");
  }

}