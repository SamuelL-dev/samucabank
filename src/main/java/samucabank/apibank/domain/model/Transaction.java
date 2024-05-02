package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @Builder
    public Transaction(Wallet sender, Wallet receiver, Integer amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
