package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import samucabank.apibank.domain.event.PaymentConfirmedEvent;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(name = "tb_transaction")
public class Transaction extends AbstractAggregateRoot<Transaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private Wallet receiver;


    @Column(nullable = false)
    private Integer amount;

    @CreationTimestamp
    private LocalDateTime transactionDate;

    public void paymentConfirmedEvent() {
        registerEvent(new PaymentConfirmedEvent(this));
    }

    @Builder
    public Transaction(Wallet sender, Wallet receiver, Integer amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
