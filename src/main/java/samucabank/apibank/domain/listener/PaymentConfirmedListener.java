package samucabank.apibank.domain.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import samucabank.apibank.domain.event.PaymentConfirmedEvent;
import samucabank.apibank.domain.model.Email;
import samucabank.apibank.domain.model.Transaction;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.model.Wallet;
import samucabank.apibank.domain.service.serviceAction.SendEmailService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentConfirmedListener {

    private final SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenMakingPayment(PaymentConfirmedEvent event) {
        final Transaction transaction = Optional.ofNullable(event)
            .map(PaymentConfirmedEvent::getTransaction)
            .orElseThrow(() -> new IllegalArgumentException("Transaction cannot be null"));

        final BigDecimal amount = Optional.ofNullable(transaction)
            .map(Transaction::getAmount)
            .orElse(BigDecimal.ZERO);

        final Email email = Optional.ofNullable(transaction)
            .map(Transaction::getSender)
            .map(Wallet::getUser)
            .map(User::getEmail)
            .orElseThrow(() -> new IllegalArgumentException("Email cannot be null"));

        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        final String formattedAmount = decimalFormat.format(amount);

        var message = SendEmailService.Message.builder()
                .subject("Payment Confirmed")
                .body("payment-confirmation.html")
                .variable("operationValue", formattedAmount)
                .recipient(email.getEmail())
                .build();

        this.sendEmailService.send(message);
    }
}
