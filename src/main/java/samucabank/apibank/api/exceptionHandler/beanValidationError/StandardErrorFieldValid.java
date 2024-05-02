package samucabank.apibank.api.exceptionHandler.beanValidationError;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"field", "message"})
public record StandardErrorFieldValid(

        @JsonProperty("field")
        String field,

        @JsonProperty("message")
        String message
) {}
