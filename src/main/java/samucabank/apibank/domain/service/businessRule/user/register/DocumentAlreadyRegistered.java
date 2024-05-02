package samucabank.apibank.domain.service.businessRule.user.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.user.DocumentAlreadyRegisteredException;

@Component
public class DocumentAlreadyRegistered implements RegisterUserValidator {
    @Override
    public void validate(final RegisterUserArgs args) {
        if(args.userRepository().existsByDocument(args.request().getDocument())){
            throw new DocumentAlreadyRegisteredException("The document provided is already associated with an active account. Please use a different document.");
        }
    }
}
