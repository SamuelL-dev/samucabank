package samucabank.apibank.api.exceptionHandler.commonStandardError;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder(value = {"timestamp", "status", "error", "path"})
public record StandardError(

        @JsonProperty("timestamp")
        Instant timestamp,

        @JsonProperty("status")
        Integer status,

        @JsonProperty("error")
        String error,

        @JsonProperty("path")
        String path
) {
}
