package exchange.rate.v2.exchangev2.errorHandling;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
@Component
public class ErrorHandling {
  
  private final String DEFAULT_FROM = "EUR"; 
  private final String DEFAULT_TO = "";
  private final int DEFAULT_AMOUNT = 1;
  private String error = "";
  
  public String getError() {
    if (error.equals("")){
      return null;
    }
    return error;
  }

  public int getDEFAULT_AMOUNT() {
    return DEFAULT_AMOUNT;
  }

  public String checkFromParameter(Optional<String> from, Map<String, Double> map){
    if (!map.keySet().contains(from.get()) || !from.isPresent()){
      error = error+"Provided From Parameter Not Valid. Defaulted To EUR. ";
          return DEFAULT_FROM;
    }
    return from.get();
  }

  public String checkToParameter(Optional<String> to, Map<String, Double> map){
    if (to.get().length()>3 || !to.isPresent()){
      String[] paramArr = to.get().split(",");
      for (int i = 0; i < paramArr.length; i++) {
        if(!map.keySet().contains(paramArr[i])){
          error = error+"Provided To Parameter Not Valid. Defaulted To Empty. ";
          return DEFAULT_TO;
        }
      }
    }
    if (!map.keySet().contains(to.get()) || !to.isPresent()){
      error = error+"Provided To Parameter Not Valid. Defaulted To Empty. ";
          return DEFAULT_TO;
    }
    return to.get();
  }

  public int checkAmountParameter(Optional<String> amount){
    Pattern p = Pattern.compile("[0-9]+");
    Matcher m = p.matcher(amount.get());
    if (m.matches()){
      int n = Integer.parseInt(amount.get());
      if (n>0){
        return n;
      }
    }
    error = error+"Provided Amount Parameter Not Valid. Defaulted To 1 (one). ";
    return DEFAULT_AMOUNT;
  }
}