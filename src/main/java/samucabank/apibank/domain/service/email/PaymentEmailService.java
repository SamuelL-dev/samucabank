package samucabank.apibank.domain.service.email;

import samucabank.apibank.domain.service.email.SendEmailService.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class PaymentEmailService {

    private final SendEmailService sendEmailService;

    public void sendEmail(Integer amount, String to) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedAmount = decimalFormat.format(amount);

        var message = Message.builder()
                .subject("Payment Confirmation")
                .body("Dear customer," +
                        "\n\nWe have processed a payment of R$" + formattedAmount + " for your account." +
                        "\n\nThank you for choosing our services.")
                .recipient(to)
                .build();

        this.sendEmailService.send(message);
    }
}
