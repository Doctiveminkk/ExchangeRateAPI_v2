package exchange.rate.v2.exchangev2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exchange.rate.v2.exchangev2.errorHandling.ErrorHandling;
import exchange.rate.v2.exchangev2.model.ResponseModel;
import lombok.AllArgsConstructor;
// Service Responsible For User Input Error Handling, BusinessLogic and Response Building
@Service
@AllArgsConstructor
public class ExchangeRateBSV {
  @Autowired
  private ExternalCallService externalCall;

  // Calculate ExchangeRate from One Currency to Another
  public ResponseModel calculateRate(Optional<String> fromParam, Optional<String> toParam){
    // Error Handling
    ErrorHandling errorHandling = new ErrorHandling();
    String from = errorHandling.checkFromParameter(fromParam, externalCall.getRatesObject().getRates());
    String to = errorHandling.checkToParameter(toParam, externalCall.getRatesObject().getRates());
    if (to.equals("")){
      return calculateAllRates(fromParam);
    }
    // Business Logic
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    rateChosenCurrency.put(to, externalCall.getRatesObject().getRates().get(to) / externalCall.getRatesObject().getRates().get(from));
    // Response Builder
    return ResponseModel.builder()
    .error(errorHandling.getError())
    .success(null)
    .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")))
    .sourceCurrency(from)
    .amount(errorHandling.getDEFAULT_AMOUNT())
    .rates(rateChosenCurrency)
    .build();
  }
  // Calculate All Rates for One Currency
  public ResponseModel calculateAllRates(Optional<String> fromParam){
    // Error Handling
    ErrorHandling errorHandling = new ErrorHandling();
    String from = errorHandling.checkFromParameter(fromParam, externalCall.getRatesObject().getRates());
    // Business Logic
    Map<String, Double> ratesChosenCurrency = new HashMap<>();
    for (String item : externalCall.getRatesObject().getRates().keySet()){
      ratesChosenCurrency.put(item, externalCall.getRatesObject().getRates().get(item) / externalCall.getRatesObject().getRates().get(from));
    }
    // Response Builder
    return ResponseModel.builder()
    .error(errorHandling.getError())
    .success(null)
    .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")))
    .sourceCurrency(from)
    .amount(errorHandling.getDEFAULT_AMOUNT())
    .rates(ratesChosenCurrency)
    .build();
  }
  // Calculate Value Conversion from One Currency to Another
  public ResponseModel convertCurrency(Optional<String> fromParam, Optional<String> toParam, Optional<String> amountParam){
    // Error Handling
    ErrorHandling errorHandling = new ErrorHandling();
    String from = errorHandling.checkFromParameter(fromParam, externalCall.getRatesObject().getRates());
    String to = errorHandling.checkToParameter(toParam, externalCall.getRatesObject().getRates());
    int amount = errorHandling.checkAmountParameter(amountParam);
    // Business Logic
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    rateChosenCurrency.put(to, (externalCall.getRatesObject().getRates().get(to) / externalCall.getRatesObject().getRates().get(from))*amount);
    // Response Builder
    return ResponseModel.builder()
    .error(errorHandling.getError())
    .success(null)
    .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")))
    .sourceCurrency(from)
    .amount(amount)
    .rates(rateChosenCurrency)
    .build();
  }
  // Calculate Value Conversion from One Currency to Several Others
  public ResponseModel convertCurrencyToMany(Optional<String> fromParam, Optional<String> toParam, Optional<String> amountParam){
    // Error Handling
    ErrorHandling errorHandling = new ErrorHandling();
    String from = errorHandling.checkFromParameter(fromParam, externalCall.getRatesObject().getRates());
    String to = errorHandling.checkToParameter(toParam, externalCall.getRatesObject().getRates());
    int amount = errorHandling.checkAmountParameter(amountParam);
    // Business Logic
    String[] currencyArr = to.split(",");
    Map<String, Double> rateChosenCurrency = new HashMap<>();
    for (int i = 0; i < currencyArr.length; i++) {
      rateChosenCurrency.put(currencyArr[i], (externalCall.getRatesObject().getRates().get(currencyArr[i]) / externalCall.getRatesObject().getRates().get(from))*amount);
    }
    // Response Builder
    return ResponseModel.builder()
    .error(errorHandling.getError())
    .success(null)
    .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")))
    .sourceCurrency(from)
    .amount(amount)
    .rates(rateChosenCurrency)
    .build();
  }
}