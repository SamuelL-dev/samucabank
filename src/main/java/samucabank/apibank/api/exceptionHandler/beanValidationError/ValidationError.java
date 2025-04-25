package samucabank.apibank.api.exceptionHandler.beanValidationError;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonPropertyOrder(value = {"timestamp", "status", "error", "validationErrors", "path"})
public class ValidationError {

        @JsonProperty("timestamp")
        Instant timestamp;

        @JsonProperty("status")
        Integer status;

        @JsonProperty("error")
        String error;

        @JsonProperty("path")
        String path;

        @JsonProperty("validationErrors")
        List<StandardErrorFieldValid> fieldErrors;
}
