package exchange.rate.v2.exchangev2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exchange.rate.v2.exchangev2.model.ResponseModel;
import exchange.rate.v2.exchangev2.service.ExchangeRateBSV;

// Controller with all api endpoints
@RestController
@RequestMapping("/api/v2")
public class ExchangeRateController {
  @Autowired
  private ExchangeRateBSV service;

  @GetMapping("/rate")
  public ResponseModel calculateRate(@RequestParam Optional<String> from,@RequestParam Optional<String> to){
    if (to.isPresent()){
      return service.calculateRate(from, to);
    }
    return service.calculateAllRates(from);
  }

  @GetMapping("/convert")
  public ResponseModel convertCurrency(@RequestParam Optional<String> from,@RequestParam Optional<String> to,@RequestParam Optional<Integer> amount){
    if (to.get().length()>3){
      return service.convertCurrencyToMany(from, to, amount);
    }
    return service.convertCurrency(from, to, amount);
  }
}