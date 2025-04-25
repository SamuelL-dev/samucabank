package samucabank.apibank.domain.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import samucabank.apibank.domain.event.UserRegisteredEvent;
import samucabank.apibank.domain.model.Email;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.service.serviceAction.SendEmailService;

import samucabank.apibank.domain.service.serviceAction.SendEmailService.Message;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenRegisteringUser(final UserRegisteredEvent event) {
        final User user = Optional.ofNullable(event)
            .map(UserRegisteredEvent::getUser)
            .orElseThrow(() -> new IllegalArgumentException("User cannot be null"));

        final Email email = Optional.ofNullable(user)
            .map(User::getEmail)
            .orElseThrow(() -> new IllegalArgumentException("User email cannot be null"));

        var message = Message.builder()
                .subject("Your account was created")
                .body("user-registered.html")
                .recipient(email.getEmail())
                .build();

        sendEmailService.send(message);
    }
}
