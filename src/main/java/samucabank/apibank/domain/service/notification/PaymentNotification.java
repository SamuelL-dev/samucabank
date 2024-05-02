package samucabank.apibank.domain.service.notification;

import org.springframework.stereotype.Component;

@Component
public class PaymentNotification implements NotificationStrategy{

    private final EmailNotificationService emailNotificationService;

    public PaymentNotification(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public void sendNotification(String to) {
        this.emailNotificationService.sendEmail(to, "Payment completed",
                "Your payment has been made.");
    }
}
