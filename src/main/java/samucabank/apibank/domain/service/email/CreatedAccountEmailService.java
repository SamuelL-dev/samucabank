package samucabank.apibank.domain.service.email;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import samucabank.apibank.domain.service.email.SendEmailService.Message;


@Component
@RequiredArgsConstructor
public class CreatedAccountEmailService {

    private final SendEmailService sendEmailService;

    public void sendEmail(String username, String to) {
        var message = Message.builder()
                .subject("Your account was created")
                .body("Congratulations <strong>"
                        + username + "</strong> your SamucaBank account has been created.")
                .recipient(to)
                .build();

        this.sendEmailService.send(message);
    }
}
