package samucabank.apibank.domain.service.serviceAction;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.model.Card;

@Component
public class CardLimitAdjustmentService {

    private static final int LOWER_LIMIT = 1500;
    private static final int MEDIUM_LIMIT = 2000;
    private static final int UPPER_LIMIT = 3500;
    private static final int MAX_LIMIT = 5000;

    public void adjustCardLimitBasedOnUserScore(Card card, int userScore) {
        if (userScore <= 400) {
            card.setLimit(LOWER_LIMIT);
        } else if (userScore <= 600) {
            card.setLimit(MEDIUM_LIMIT);
        } else if (userScore <= 800) {
            card.setLimit(UPPER_LIMIT);
        } else {
            card.setLimit(MAX_LIMIT);
        }
    }
}