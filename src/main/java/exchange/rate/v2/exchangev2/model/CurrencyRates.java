package exchange.rate.v2.exchangev2.model;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

// Representation of data provided by external service
@Component
@Data
public class CurrencyRates {
  private Map<String, Double> rates;
}