package exchange.rate.v2.exchangev2.errorHandling;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;


public class ErrorHandlingTest {

  @Test
  void testIsPresentCondition() {
     // given
     Optional<String> from = Optional.empty();
     // then
     assertFalse(from.isPresent());
  }

  @Test
  void testParameterLength() {
    // given
    Optional<String> to = Optional.of("abcd");
    // then
    assertTrue(to.get().length()>3);
  }

  @Test
  void testIfValueExistsInMap() {
    // given
    Map<String, Double> rates = new HashMap<String,Double>();
    rates.put("AED", 3.672605);
    rates.put("AFN", 87.509439);
    rates.put("CVE", 114.116757);
    Optional<String> from = Optional.of("AED");
    Optional<String> to = Optional.of("EUR");
    // then
    assertTrue(rates.keySet().contains(from.get()));
    assertFalse(rates.keySet().contains(to.get()));
  }

  @Test
  void testRegularExpression() {
    // given
    Pattern p = Pattern.compile("[0-9]+");
    Optional<String> realAmount = Optional.of("1");
    Optional<String> fakeAmount = Optional.of("t");
    Matcher r = p.matcher(realAmount.get());
    Matcher f = p.matcher(fakeAmount.get());
    // then
    assertTrue(r.matches());
    assertFalse(f.matches());
  }
}
