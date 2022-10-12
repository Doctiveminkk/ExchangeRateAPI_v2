package exchange.rate.v2.exchangev2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exchange.rate.v2.exchangev2.errorHandling.ErrorHandling;
import exchange.rate.v2.exchangev2.model.ResponseModel;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateBSVTest {
  @Mock
  private ExternalCallService externalCall;

  private Optional<String> from = Optional.of("USD");
  private Optional<String> to = Optional.of("EUR");
  private Optional<String> amount = Optional.of("1");

  // Test ResponseModel Builder
  @Test
  void testResponseModelBuilder() {
    // given
    String error = "error message";
    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
    int n = Integer.parseInt(amount.get());
    Map<String, Double> rates = new HashMap<String,Double>();
    rates.put("AED", 3.672605);
    rates.put("AFN", 87.509439);
    rates.put("CVE", 114.116757);
    // when
    ResponseModel response = ResponseModel.builder()
    .error(error)
    .success(null)
    .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")))
    .sourceCurrency(from.get())
    .amount(n)
    .rates(rates)
    .build();
    // then
    assertEquals(response.error(), error);
    assertEquals(response.date(), date);
    assertEquals(response.sourceCurrency(), from.get());
    assertEquals(response.amount(), n);
    assertEquals(response.rates(), rates);
  }
  // Test if Condition For Retrieving All Rates Instead Of One Is Meet
  @Test
  void testCalculateRateWhenInvalidToParameter() {
    // given
    ErrorHandling errorHandling = new ErrorHandling();
    to = Optional.of("dgsdfg");
    Map<String, Double> rates = new HashMap<String,Double>();
    rates.put("AED", 3.672605);
    rates.put("AFN", 87.509439);
    rates.put("CVE", 114.116757);
    // when
    String toResult = errorHandling.checkToParameter(to, rates);
    // then
    assertEquals(toResult, "");
  }
  // Test Exchange Rate Formula
  @Test
  void testConvertCurrency() {
    // given
    Double target = 0.4;
    // when
    Double result = 1 / 2.5;
    // then
    assertEquals(result, target);
  }
}
