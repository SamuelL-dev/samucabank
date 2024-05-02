package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.user.PhoneAlreadyRegisteredException;

@Component
public class PhoneAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(final RegisterUserArgs args) {
        if(args.userRepository().existsByPhoneNumber(args.request().getPhoneNumber())){
            throw new PhoneAlreadyRegisteredException("The phone number is already associated with an active account. Please use a different phone number.");
        }
    }
}
