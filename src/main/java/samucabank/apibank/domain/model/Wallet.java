package samucabank.apibank.domain.model;


import jakarta.persistence.*;
import lombok.*;
import samucabank.apibank.domain.enums.wallet.Currency;

import java.util.UUID;


@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity(name = "Wallets")
public class Wallet {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Integer balance;

    @OneToOne
    private User user;

    @Builder
    public Wallet(
        Currency currency,
        Integer balance,
        User user
    ) {
        this.currency = currency;
        this.balance = balance;
        this.user = user;
    }
}
