package samucabank.apibank.domain.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import samucabank.apibank.domain.event.UserRegisteredEvent;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.service.serviceAction.SendEmailService;

import samucabank.apibank.domain.service.serviceAction.SendEmailService.Message;


@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final SendEmailService sendEmailService;

    @EventListener
    public void whenRegisteringUser(final UserRegisteredEvent event) {
        final User user = event.getUser();

        var message = Message.builder()
                .subject("Your account was created")
                .body("user-registered.html")
                .recipient(user.getEmail())
                .build();

        this.sendEmailService.send(message);
    }
}
