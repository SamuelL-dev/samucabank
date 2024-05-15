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
    public void validate(final CardTransactionArgs args) {
        final Integer transactionAmount = args.request().amount();
        final Integer cardLimit = args.card().getCardLimit();
        final Integer amountSpent = args.card().getAmountSpent();

        if(getType() == args.request().type() && transactionAmount > cardLimit - amountSpent)
            throw new CardLimitExceededException("The card limit is insufficient to carry out the transaction.");
        }
    }