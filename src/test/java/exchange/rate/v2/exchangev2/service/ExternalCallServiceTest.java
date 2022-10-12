package exchange.rate.v2.exchangev2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import exchange.rate.v2.exchangev2.model.CurrencyRates;

@ExtendWith(MockitoExtension.class)
public class ExternalCallServiceTest {
  @Mock
  private CurrencyRates ratesObject;

  private ExternalCallService testSubject;

  private final String SCHEME = "https";
  private final String HOST = "api.exchangerate.host";
  private final String LATEST_RATES = "latest";

  // Initializes Class Under Testing
  @BeforeEach
  void setUp(){
    testSubject= new ExternalCallService(ratesObject);
  }
  // External API Call Method
  @Test
  void testCallExternalAPI() {
    // given
    UriComponents uri = UriComponentsBuilder.newInstance()
    .scheme(SCHEME)
    .host(HOST)
    .path(LATEST_RATES)
    .build();
    // when
    CurrencyRates methodResult = testSubject.getRatesObject();
    //then
    assertEquals(uri.getScheme(), SCHEME);
    assertEquals(uri.getHost(), HOST);
    assertEquals(uri.getPath(), LATEST_RATES);
    assertEquals(methodResult.getClass(), ratesObject.getClass());
  }
  // Getter Integrity
  @Test
  void testGetRatesObject() {
    // given
    Map<String, Double> rates = new HashMap<String,Double>();
    rates.put("AED", 3.672605);
    rates.put("AFN", 87.509439);
    rates.put("CVE", 114.116757);
    ratesObject.setRates(rates);
    // when
    CurrencyRates methodResult = testSubject.getRatesObject();
    // then
    assertEquals(ratesObject.getRates(), methodResult.getRates());
  }
}
