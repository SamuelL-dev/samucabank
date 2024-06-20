package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import samucabank.apibank.domain.enums.user.Gender;
import samucabank.apibank.domain.enums.user.MaritalStatus;
import samucabank.apibank.domain.event.UserRegisteredEvent;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(name = "tb_user")
public class User extends AbstractAggregateRoot<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String document;

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private int score;

    @OneToOne(mappedBy = "user")
    private Card card;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

    public void userRegisteredEvent(){
        registerEvent(new UserRegisteredEvent(this));
    }
}
