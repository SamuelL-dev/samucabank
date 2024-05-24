package samucabank.apibank.domain.service.businessRule.card.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.card.CardAlreadyRegisteredException;

@Component
public class CardAlreadyRegistered implements RegisterCardValidator {
    @Override
    public void validate(RegisterCardArgs args) {
        if(args.user().getCard() != null) {
            throw new CardAlreadyRegisteredException("This user already has a card.");
        }
    }
}
