package samucabank.apibank.domain.service.email;

import jakarta.mail.MessagingException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface SendEmailService {

    void send(Message message);

    @Getter
    @Builder
    class Message {

        @Singular
        private Set<String> recipients;

        @NonNull
        private String subject;

        @NonNull
        private String body;
    }
}
