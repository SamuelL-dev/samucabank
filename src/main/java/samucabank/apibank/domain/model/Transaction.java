package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import samucabank.apibank.domain.event.PaymentConfirmedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(name = "Transactions")
public class Transaction extends AbstractAggregateRoot<Transaction> {

    @Id private final String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "senderId") private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiverId") private Wallet receiver;

    @Column(nullable = false) private BigDecimal amount;

    @CreationTimestamp private LocalDateTime date;

    public void paymentConfirmedEvent() {
        registerEvent(new PaymentConfirmedEvent(this));
    }

    @Builder
    public Transaction(
            Wallet sender,
            Wallet receiver,
            BigDecimal amount
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
