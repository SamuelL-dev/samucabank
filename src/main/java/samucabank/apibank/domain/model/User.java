package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import samucabank.apibank.domain.enums.user.Gender;
import samucabank.apibank.domain.enums.user.MaritalStatus;
import samucabank.apibank.domain.enums.user.UserRole;
import samucabank.apibank.domain.event.UserRegisteredEvent;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(name = "Users")
public class User extends AbstractAggregateRoot<User> {

    @Id private String id = UUID.randomUUID().toString();

    @Column(nullable = false) private String firstName;

    @Column(nullable = false) private String lastName;

    @Column(nullable = false) private String password;

    @Column(nullable = false, unique = true) private Email email;

    @Column(nullable = false, unique = true) private final Document document;

    @Embedded private Address address;

    @Column(nullable = false, unique = true) private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false) private LocalDate dateOfBirth;

    @Column(nullable = false) @Enumerated(EnumType.STRING) private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING) @Column(nullable = false) private Gender gender;

    private int score;

    @OneToOne(mappedBy = "user") private Card card;

    @OneToOne(mappedBy = "user") private Wallet wallet;

    @Enumerated(EnumType.STRING) @Column(nullable = false) private UserRole role;

    public void addRole(UserRole role) {
        this.role = role;
    }

    public void userRegisteredEvent() {
        registerEvent(new UserRegisteredEvent(this));
    }

    @Builder
    public User(
        String firstName,
        String lastName,
        String password,
        Email email,
        Document document,
        Address address,
        String phoneNumber,
        LocalDate dateOfBirth,
        MaritalStatus maritalStatus,
        Gender gender,
        int score,
        Card card,
        Wallet wallet,
        UserRole role
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.document = document;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.gender = gender;
        this.score = score;
        this.card = card;
        this.wallet = wallet;
        this.role = role;
    }
}
