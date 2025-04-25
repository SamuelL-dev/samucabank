package samucabank.apibank.api.dtos.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.enums.user.Gender;
import samucabank.apibank.domain.enums.user.MaritalStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

        @NotBlank private String firstName;

        @NotBlank private String lastName;

        @NotBlank @Size(min = 6, max = 14) private String password;

        @NotBlank private String email;

        @NotBlank private String document;

        @NotBlank @Size(min = 8, max = 8) private String cep;

        @NotBlank private String addressNumber;

        @NotBlank private String phoneNumber;

        @NotNull @JsonFormat(pattern = "dd-MM-yyyy") private LocalDate dateOfBirth;

        @NotNull private MaritalStatus maritalStatus;

        @NotNull private Gender gender;
}
