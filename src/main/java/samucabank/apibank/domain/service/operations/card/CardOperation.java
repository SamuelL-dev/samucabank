package samucabank.apibank.domain.service.operations.card;

import samucabank.apibank.domain.enums.card.CardOperationType;

public interface CardOperation {

    CardOperationType getType();
    void applyCardTransactionOperation(CardOperationArgs args);
}
