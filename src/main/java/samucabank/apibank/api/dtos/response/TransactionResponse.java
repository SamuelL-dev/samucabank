package samucabank.apibank.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.enuns.transaction.TransactionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"processedAt", "description", "status"})
public class TransactionResponse {

    private TransactionStatus status;

    private String description;

    private LocalDateTime processedAt;

    public TransactionResponse(TransactionStatus status, String description) {
        this.status = status;
        this.description = description;
        this.processedAt = LocalDateTime.now();
    }
}
