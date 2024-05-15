package samucabank.apibank.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import samucabank.apibank.domain.model.User;


@Getter
@AllArgsConstructor
public class UserRegisteredEvent {

    private final User user;

}
