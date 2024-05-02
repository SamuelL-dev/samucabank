package samucabank.apibank.domain.service.businessRule.card.checkEligibility;

import samucabank.apibank.domain.model.Card;
import samucabank.apibank.domain.model.User;
public record CardEligibilityArgs(User user, Card card) {
}
