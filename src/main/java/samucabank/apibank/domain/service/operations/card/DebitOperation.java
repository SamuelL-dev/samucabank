package samucabank.apibank.domain.service.operations.card;

import org.springframework.stereotype.Component;
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.domain.enums.card.CardOperationType;

import java.util.Optional;

@Component
public class DebitOperation implements CardOperation {
    @Override
    public CardOperationType getType() {
        return CardOperationType.DEBIT;
    }

    @Override
    public void apply(CardOperationArgs args) {
        CardOperationType request = Optional.ofNullable(args.data())
            .map(CardTransactionRequest::type)
            .orElseThrow(() -> new IllegalArgumentException("Operation type cannot be null"));

        if (request == getType()) {
            args.wallet().setBalance(args.wallet().getBalance() - args.data().amount());
        }
    }
}
