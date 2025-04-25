package samucabank.apibank.domain.service.businessRule.card.transaction;

import samucabank.apibank.domain.enums.card.CardOperationType;

public interface CardTransactionValidator {

    CardOperationType getType();

    void validate(CardTransactionArgs args);
}
