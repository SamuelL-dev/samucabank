package samucabank.apibank.infrastructure.card;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class CardDataGenerator {
    public String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for(int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }
    public String generateCVV() {
        Random random = new Random();
        int cvvNumber = 100 + random.nextInt(900);
        return String.valueOf(cvvNumber);
    }
    public LocalDate generateExpirationDate() {
        Random random = new Random();

        int yearsToAdd = 1 + random.nextInt(8);

        LocalDate currentDate = LocalDate.now();

        return currentDate.plusYears(yearsToAdd);
    }
}
