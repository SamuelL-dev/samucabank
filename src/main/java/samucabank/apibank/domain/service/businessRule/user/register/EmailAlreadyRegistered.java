package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.domain.service.customException.user.EmailAlreadyRegisteredException;

import java.util.Optional;

@Component
public class EmailAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(RegisterUserArgs args) {

        final String email = Optional.ofNullable(args)
            .map(RegisterUserArgs::data)
            .map(UserRequest::getEmail)
            .orElseThrow(() -> new IllegalArgumentException("This user email already registered"));

        if (args.userRepository().existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException(
                "The email address you provided is already associated with an active account. Please use a different email address."
            );
        }
    }
}
