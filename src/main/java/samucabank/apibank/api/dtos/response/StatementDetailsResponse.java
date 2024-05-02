package samucabank.apibank.api.dtos.response;

import lombok.Getter;
import lombok.Setter;
import samucabank.apibank.domain.model.Transaction;

import java.util.List;

@Getter
@Setter
public class StatementDetailsResponse {

    private List<StatementSenderResponse> transferSent;

    private List<StatementReceiverResponse> transferReceived;

    public StatementDetailsResponse(List<Transaction> sentTransactions, List<Transaction> receivedTransactions) {
        this.transferSent = sentTransactions.stream()
                .map(StatementSenderResponse::new)
                .toList();

        this.transferReceived = receivedTransactions.stream()
                .map(StatementReceiverResponse::new)
                .toList();
    }
}
