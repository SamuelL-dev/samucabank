package samucabank.apibank.domain.service.operations.card;

import samucabank.apibank.domain.enuns.card.CardOperationType;

public interface CardOperation {

    CardOperationType getType();
    void applyCardTransactionOperation(CardOperationArgs args);
}
