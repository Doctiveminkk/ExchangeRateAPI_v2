package exchange.rate.v2.exchangev2.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exchange.rate.v2.exchangev2.service.ExchangeRateBSV;
@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {
  @Mock
  private ExchangeRateBSV service;
  private ExchangeRateController testSubject;

  private Optional<String> from = Optional.of("USD");
  private Optional<String> to = Optional.of("EUR");
  private Optional<String> amount = Optional.of("1");

  // Initializes Class Under Testing
  @BeforeEach
  void setUp(){
    testSubject= new ExchangeRateController(service);
  }
  // Test All Rates Method Invocation
  @Test
  void testCalculateAllRates() {
    // given
    Optional<String> to = Optional.empty();
    // when
    testSubject.calculateRate(from, to);
    // then
    assertFalse(to.isPresent());
    verify(service).calculateAllRates(from);    
  }
  // Test Single Rate Method Invocation
  @Test
  void testCalculateSingleRate() {
    // when
    testSubject.calculateRate(from, to);
    // then
    assertTrue(to.isPresent());
    verify(service).calculateRate(from, to);    
  }
  // Test Currency Conversion To Many Method Invocation
  @Test
  void testConvertCurrencyToMany() {
    // given
    to = Optional.of("EUR,AUD,CZK");
    // when
      testSubject.convertCurrency(from, to, amount);
    // then
      assertTrue(to.get().length()>3);
      verify(service).convertCurrencyToMany(from, to, amount);    
  }
  // Test Currency Conversion Method Invocation
  @Test
  void testConvertCurrency() {
    // when
      testSubject.convertCurrency(from, to, amount);
    // then
      assertFalse(to.get().length()>3);
      verify(service).convertCurrency(from, to, amount);    
  }
}
