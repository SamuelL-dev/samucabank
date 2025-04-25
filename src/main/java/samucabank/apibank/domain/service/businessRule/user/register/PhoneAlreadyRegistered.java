package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.domain.service.customException.user.PhoneAlreadyRegisteredException;

import java.util.Optional;

@Component
public class PhoneAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(final RegisterUserArgs args) {

        final String phoneNumber = Optional.ofNullable(args)
            .map(RegisterUserArgs::data)
            .map(UserRequest::getPhoneNumber)
            .orElseThrow(() -> new IllegalArgumentException("This phone is already registered"));

        if (args.userRepository().existsByPhoneNumber(phoneNumber)) {
            throw new PhoneAlreadyRegisteredException(
                "The phone number is already associated with an active account. Please use a different phone number."
            );
        }
    }
}