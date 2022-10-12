package exchange.rate.v2.exchangev2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exchange.rate.v2.exchangev2.model.ResponseModel;

@Service
public class ExchangeRateBSV {
  @Autowired
  private ExternalCallService externalCall;

  private final int DEFAULT_AMOUNT = 1;


  // !!!!!!!!
  private String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));

  // Calculate ExchangeRate from One Currency to Another
  public ResponseModel calculateRate(Optional<String> from, Optional<String> to){
    // Business Logic
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    rateChosenCurrency.put(to.get(), externalCall.getRates().getRates().get(to.get()) / externalCall.getRates().getRates().get(from.get()));
    // Response Builder
    return ResponseModel.builder()
    .error("")
    .success(null)
    .date(date)
    .sourceCurrency(from.get())
    .amount(DEFAULT_AMOUNT)
    .rates(rateChosenCurrency)
    .build();
  }
  // Calculate All Rates for One Currency
  public ResponseModel calculateAllRates(Optional<String> from){
    // Business Logic
    Map<String, Double> ratesChosenCurrency = new HashMap<>();
    for (String item : externalCall.getRates().getRates().keySet()){
      ratesChosenCurrency.put(item, externalCall.getRates().getRates().get(item) / externalCall.getRates().getRates().get(from.get()));
    }
    // Response Builder
    return ResponseModel.builder()
    .error("")
    .success(null)
    .date(date)
    .sourceCurrency(from.get())
    .amount(DEFAULT_AMOUNT)
    .rates(ratesChosenCurrency)
    .build();
  }
  // Calculate Value Conversion from One Currency to Another
  public ResponseModel convertCurrency(Optional<String> from, Optional<String> to, Optional<Integer> amount){
    // Business Logic
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    rateChosenCurrency.put(to.get(), (externalCall.getRates().getRates().get(to.get()) / externalCall.getRates().getRates().get(from.get()))*amount.get());
    // Response Builder
    return ResponseModel.builder()
    .error("")
    .success(null)
    .date(date)
    .sourceCurrency(from.get())
    .amount(amount.get())
    .rates(rateChosenCurrency)
    .build();
  }
  // Calculate Value Conversion from One Currency to Several Others
  public ResponseModel convertCurrencyToMany(Optional<String> from, Optional<String> to, Optional<Integer> amount){
    // Business Logic
    String[] currencyArr = to.get().split(",");
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    for (int i = 0; i < currencyArr.length; i++) {
      rateChosenCurrency.put(currencyArr[i], (externalCall.getRates().getRates().get(currencyArr[i]) / externalCall.getRates().getRates().get(from.get()))*amount.get());
    }
    // Response Builder
    return ResponseModel.builder()
    .error("")
    .success(null)
    .date(date)
    .sourceCurrency(from.get())
    .amount(amount.get())
    .rates(rateChosenCurrency)
    .build();
  }
}