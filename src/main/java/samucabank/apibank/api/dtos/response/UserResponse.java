package samucabank.apibank.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.enuns.user.Gender;
import samucabank.apibank.domain.enuns.user.MaritalStatus;
import samucabank.apibank.domain.model.Address;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"firstName", "lastName", "gender", "maritalStatus", "email", "document", "phoneNumber", "dateOfBirth", "walletId", "cardId", "address"})
public class UserResponse {

    private String firstName;

    private String lastName;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private String email;

    private String document;

    private Address address;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String walletId;

    private String cardId;
}
