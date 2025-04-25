package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.domain.service.customException.user.AgeEligibleForRegistrationException;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class AgeEligibleForRegistration implements RegisterUserValidator {

    @Override
    public void validate(RegisterUserArgs args) {
        final LocalDate dateOfBirth = Optional.ofNullable(args)
            .map(RegisterUserArgs::data)
            .map(UserRequest::getDateOfBirth)
            .orElseThrow(() -> new IllegalArgumentException("Date of birth cannot be null"));

        if (dateOfBirth.isAfter(LocalDate.now().minusYears(16))) {
            throw new AgeEligibleForRegistrationException(
                "Your account opening data cannot be approved due to the current eligibility criteria."
            );
        }
    }
}
