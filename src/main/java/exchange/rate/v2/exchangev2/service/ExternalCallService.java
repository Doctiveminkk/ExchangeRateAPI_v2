package exchange.rate.v2.exchangev2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import exchange.rate.v2.exchangev2.model.CurrencyRates;

@Service
public class ExternalCallService {
  
  private final String SCHEME = "https";
  private final String HOST = "api.exchangerate.host";
  private final String LATEST_RATES = "latest";
  @Autowired
  private CurrencyRates ratesObject;

  public CurrencyRates getRatesObject() {
    return ratesObject;
  }

  //Scheduled External API Call to Retrieve Currency Rates Every One Minute
  @Scheduled(fixedRate = 60000)
  public void callExternalAPI(){
    RestTemplate template = new RestTemplate();
    // Create Request URL
    UriComponents uri = UriComponentsBuilder.newInstance()
    .scheme(SCHEME)
    .host(HOST)
    .path(LATEST_RATES)
    .build();
    // Build CurrencyRates Object Based on ExternalResponse Data
    ratesObject = template.getForObject(uri.toUriString(), CurrencyRates.class);
  }
}