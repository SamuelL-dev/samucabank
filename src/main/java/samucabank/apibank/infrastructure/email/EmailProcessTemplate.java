package samucabank.apibank.infrastructure.email;


import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import samucabank.apibank.domain.service.serviceAction.SendEmailService.Message;

@Component
@RequiredArgsConstructor
public class EmailProcessTemplate {

    private final Configuration freemarkerConfig;

    public String templateProcess(final Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("Unable to create email template", e);
        }
    }
}
