package exchange.rate.v2.exchangev2.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

// Representation of Api Response Structure
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record ResponseModel(
  String error, 
  Boolean success, 
  String date, 
  String sourceCurrency,
  int amount,
  Map<String, Double> rates){
  }