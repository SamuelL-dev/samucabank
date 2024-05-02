package samucabank.apibank.domain.service.notification;

import org.springframework.stereotype.Component;

@Component
public class AccountCreationNotification implements NotificationStrategy {

    private final EmailNotificationService emailNotificationService;

    public AccountCreationNotification(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public void sendNotification(String to) {
        this.emailNotificationService.sendEmail(to, "Your account was created",
                "Congratulations, your SamucaBank account has been created.");
    }
}