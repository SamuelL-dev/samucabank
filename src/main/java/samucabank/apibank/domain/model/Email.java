package samucabank.apibank.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class Email {

    private final Pattern EMAIL_REGEX = Pattern.compile(
        "(^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$)"
    );

    private String email;

    public Email(String email) {
        this.validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email) {
        if(email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        if(EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
