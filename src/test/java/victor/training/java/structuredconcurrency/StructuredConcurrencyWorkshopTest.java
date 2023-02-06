package victor.training.java.structuredconcurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.structuredconcurrency.StructuredConcurrencyWorkshop.ApiClient;
import victor.training.java.structuredconcurrency.StructuredConcurrencyWorkshop.BookingOffersDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StructuredConcurrencyWorkshopTest {
  @Mock
  ApiClient apiClient;
  @InjectMocks
  StructuredConcurrencyWorkshop workshop;

  @Test
  @Timeout(unit = MILLISECONDS, value = 150)
  void parallel() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(100, new Returns("Rain")));

    BookingOffersDto r = workshop.parallel();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Rain"));
  }

  @Test
  @Timeout(unit = MILLISECONDS, value = 600)
  void timeoutInTime() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(100, new Returns("Rain")));

    BookingOffersDto r = workshop.timeout();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Rain"));
  }

  @Test
  @Timeout(unit = MILLISECONDS, value = 600)
  void timeoutTimeoutsWeather() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(100, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(700, new Returns("Rain")));

    BookingOffersDto r = workshop.timeout();
    assertThat(r).isEqualTo(new BookingOffersDto(List.of("offer"), "Probably Sunny"));
  }
  @Test
  @Timeout(unit = MILLISECONDS, value = 600)
  void timeoutTimeoutsOffers() throws InterruptedException {
    when(apiClient.getBookingOffers(1)).thenAnswer(new AnswersWithDelay(600, new Returns(List.of("offer"))));
    when(apiClient.getWeather()).thenAnswer(new AnswersWithDelay(200, new Returns("Rain")));

    workshop.timeout();
//    assertThatThrownBy(() -> workshop.timeout());
  }
}