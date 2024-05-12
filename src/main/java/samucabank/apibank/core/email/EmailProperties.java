
package samucabank.apibank.core.email;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("samucabank.email")
public class EmailProperties {

    @NotBlank
    private String from;
}
