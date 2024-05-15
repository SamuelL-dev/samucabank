package samucabank.apibank.domain.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import samucabank.apibank.domain.event.PaymentConfirmedEvent;
import samucabank.apibank.domain.model.Transaction;
import samucabank.apibank.domain.service.serviceAction.SendEmailService;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class PaymentConfirmedListener {

    private final SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenMakingPayment(PaymentConfirmedEvent event) {
        final Transaction transaction = event.getTransaction();

        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        final String formattedAmount = decimalFormat.format(transaction.getAmount());

        var message = SendEmailService.Message.builder()
                .subject("Payment Confirmed")
                .body("payment-confirmation.html")
                .variable("operationValue", formattedAmount)
                .recipient(transaction.getSender().getUser().getEmail())
                .build();

        this.sendEmailService.send(message);
    }
}
