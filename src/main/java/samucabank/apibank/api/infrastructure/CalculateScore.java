package samucabank.apibank.api.infrastructure;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.model.User;

import java.time.LocalDate;

@Component
public class CalculateScore {

    public void calculateScore(final User user) {
        int genderWeight = 7;
        int maritalStatusWeight = 3;
        int dateOfBirthWeight = 5;


        switch (user.getGender()) {
            case MAN -> genderWeight *= 0.6;
            case WOMAN -> genderWeight *= 0.4;
        }

        switch (user.getMaritalStatus()) {
            case MARRIED -> maritalStatusWeight *= 0.4;
            case SINGLE -> maritalStatusWeight += 0.6;
            case WIDOWER -> maritalStatusWeight *= 0.5;
            case DIVORCED -> maritalStatusWeight *= 0.2;
        }

        final LocalDate currentDate = LocalDate.now();

        int age = currentDate.getYear() - user.getDateOfBirth().getYear();
        if (age >= 18) {
            dateOfBirthWeight *= 0.7;
        } else if (age < 25) {
            dateOfBirthWeight *= 0.5;
        } else if (age < 50) {
            dateOfBirthWeight *= 0.1;
        }

        int score = genderWeight * 100 + maritalStatusWeight * 100 + dateOfBirthWeight * 100;

        user.setScore(score);
    }
}
