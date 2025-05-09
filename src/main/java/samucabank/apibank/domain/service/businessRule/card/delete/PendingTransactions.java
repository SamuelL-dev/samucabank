package samucabank.apibank.domain.service.businessRule.card.delete;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.card.CardPendingTransactionException;

@Component
public class PendingTransactions implements DeleteCardValidator{

    @Override
    public void validate(final DeleteCardArgs args) {
        if(args.card().getAmountSpent() != 0) {
            throw new CardPendingTransactionException(
                "Cannot delete the card. There are pending transactions associated with it. Please pay off the pending balance before proceeding with the deletion."
            );
        }
    }
}