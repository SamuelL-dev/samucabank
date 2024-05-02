package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.user.AgeEligibleForRegistrationException;

import java.time.LocalDate;

@Component
public class AgeEligibleForRegistration implements RegisterUserValidator{

    @Override
    public void validate(final RegisterUserArgs args) {
        final LocalDate dateOfBirth = args.request().getDateOfBirth();
        final LocalDate today = LocalDate.now();
        final LocalDate eighteenYearsAgo = today.minusYears(16);

        if(dateOfBirth.isAfter(eighteenYearsAgo)) {
            throw new AgeEligibleForRegistrationException("Your account opening request cannot be approved due to the current eligibility criteria.");
        }
    }
}
