package samucabank.apibank.domain.service.businessRule.card.checkEligibility;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.user.UserNotEligibleForCreditException;

@Component
public class CardEligibilityAssessor implements CardEligibilityValidator {

    private static final int MINIMUM_SCORE = 300;

    @Override
    public void checkEligibility(CardEligibilityArgs args) {
        int userScore = args.user().getScore();

        if (userScore <= MINIMUM_SCORE) {
            throw new UserNotEligibleForCreditException("Sorry, but you are not eligible for credit due to your credit score. Please improve your score before trying again.");
        }
    }
}