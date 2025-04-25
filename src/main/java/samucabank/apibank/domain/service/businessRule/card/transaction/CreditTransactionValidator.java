package samucabank.apibank.domain.service.businessRule.card.transaction;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.enums.card.CardOperationType;
import samucabank.apibank.domain.service.customException.card.CardLimitExceededException;

@Component
public class CreditTransactionValidator implements CardTransactionValidator {
    @Override
    public CardOperationType getType() {
        return CardOperationType.CREDIT;
    }

    @Override
    public void validate(CardTransactionArgs args) {
        final int amount = args.data().amount();
        final int cardLimit = args.card().getLimit();
        final int amountSpent = args.card().getAmountSpent();

        if (getType() == args.data().type() && amount > cardLimit - amountSpent)
            throw new CardLimitExceededException(
                "The card limit is insufficient to carry out the transaction."
            );
    }
}