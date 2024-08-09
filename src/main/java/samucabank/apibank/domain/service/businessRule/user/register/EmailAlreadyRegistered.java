package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.user.EmailAlreadyRegisteredException;

@Component
public class EmailAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(final RegisterUserArgs args) {
        if(args.userRepository().existsByEmail(args.request().getEmail())){
            throw new EmailAlreadyRegisteredException("The email address you provided is already associated with an active account. Please use a different email address.");
        }
}
}
