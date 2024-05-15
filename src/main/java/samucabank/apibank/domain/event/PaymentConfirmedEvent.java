package samucabank.apibank.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import samucabank.apibank.domain.model.Transaction;

@Getter
@AllArgsConstructor
public class PaymentConfirmedEvent {

    private final Transaction transaction;
}
