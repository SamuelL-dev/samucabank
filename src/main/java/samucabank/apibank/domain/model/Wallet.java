package samucabank.apibank.domain.model;


import jakarta.persistence.*;
import lombok.*;
import samucabank.apibank.domain.enuns.wallet.Currency;



@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity(name = "tb_wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Integer balance;

    @OneToOne
    private User user;

    @Builder
    public Wallet(Currency currency, Integer balance, User user) {
        this.currency = currency;
        this.balance = balance;
        this.user = user;
    }
}
