package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.repositories.UserRepository;
import samucabank.apibank.domain.service.customException.user.EmailAlreadyRegisteredException;

import java.util.Optional;

@Component
public class EmailAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(final RegisterUserArgs args) {
        String email = args.request().getEmail();

        Optional<User> user = args.userRepository().findByEmail(email);

        if(user.isPresent()) {
            throw new EmailAlreadyRegisteredException("The email address you provided is already associated with an active account. Please use a different email address.");
        }
    }
}
