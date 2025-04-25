package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.domain.service.customException.user.DocumentAlreadyRegisteredException;

import java.util.Optional;

@Component
public class DocumentAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(RegisterUserArgs args) {

        final String document = Optional.ofNullable(args)
            .map(RegisterUserArgs::data)
            .map(UserRequest::getDocument)
            .orElseThrow(() -> new IllegalArgumentException("User document cannot be null"));

        if (args.userRepository().existsByDocument(document)) {
            throw new DocumentAlreadyRegisteredException(
                "The document provided is already associated with an active account. Please use a different document."
            );
        }
    }
}
