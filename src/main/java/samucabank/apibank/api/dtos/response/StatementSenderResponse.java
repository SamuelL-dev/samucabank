package samucabank.apibank.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.model.Transaction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"date", "to", "amount", "transactionDate", "transactionId"})
public class StatementSenderResponse {

    private String transactionId;

    private Integer amount;

    private LocalDateTime transactionDate;

    private String to;

    public StatementSenderResponse(Transaction transaction) {
        this.transactionId = transaction.getId();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.to = transaction.getReceiver().getId();
    }
}
