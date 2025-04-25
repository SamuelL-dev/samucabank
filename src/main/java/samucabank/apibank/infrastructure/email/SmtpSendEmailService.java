package samucabank.apibank.infrastructure.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import samucabank.apibank.core.email.EmailProperties;
import samucabank.apibank.domain.service.serviceAction.SendEmailService;

@Service
@RequiredArgsConstructor
public class SmtpSendEmailService implements SendEmailService {

    private final JavaMailSender javaMailSender;

    private final EmailProperties emailProperties;

    private final EmailProcessTemplate emailProcessTemplate;

    @Override
    public void send(Message message) {
        try {
            final MimeMessage mimeMessage = createMimeMessage(message);
            this.javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Unable to send an email", e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        final String body = emailProcessTemplate.templateProcess(message);

        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getFrom());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);

        return mimeMessage;
    }
}
