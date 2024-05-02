package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import samucabank.apibank.domain.enuns.user.Gender;
import samucabank.apibank.domain.enuns.user.MaritalStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
}
