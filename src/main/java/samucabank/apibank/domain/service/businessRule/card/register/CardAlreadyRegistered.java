package samucabank.apibank.domain.service.businessRule.card.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.model.Card;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.service.customException.card.CardAlreadyRegisteredException;

import java.util.Optional;

@Component
public class CardAlreadyRegistered implements RegisterCardValidator {

    @Override
    public void validate(RegisterCardArgs args) {

        final User user = Optional.of(args)
            .map(RegisterCardArgs::user)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        final Card card = Optional.of(user)
            .map(User::getCard)
            .orElseThrow(() -> new CardAlreadyRegisteredException("Card not found"));

        if (card != null) {
            throw new CardAlreadyRegisteredException("This user already has a card.");
        }

    }
}
