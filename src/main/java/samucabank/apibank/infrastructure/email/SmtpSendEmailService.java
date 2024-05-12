package samucabank.apibank.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import samucabank.apibank.core.email.EmailProperties;
import samucabank.apibank.domain.service.email.SendEmailService;

@Service
@RequiredArgsConstructor
public class SmtpSendEmailService implements SendEmailService {

    private final JavaMailSender javaMailSender;

    private final EmailProperties emailProperties;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);
            this.javaMailSender.send(mimeMessage);
        } catch(Exception e) {
            throw new EmailException("Unable to send an email", e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getFrom());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(message.getBody(), true);

        return mimeMessage;
    }
}
