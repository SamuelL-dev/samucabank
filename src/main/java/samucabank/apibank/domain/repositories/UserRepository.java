package samucabank.apibank.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samucabank.apibank.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByDocument(String document);

    boolean existsByEmail(String email);
}

