package samucabank.apibank.domain.service.serviceAction;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.api.dtos.response.UserResponse;
import samucabank.apibank.infrastructure.viacep.ViaCepClientImpl;
import samucabank.apibank.domain.model.Address;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.repositories.UserRepository;
import samucabank.apibank.domain.service.businessRule.user.register.RegisterUserArgs;
import samucabank.apibank.domain.service.businessRule.user.register.RegisterUserValidator;
import samucabank.apibank.domain.service.customException.user.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final ViaCepClientImpl viaCepService;

    private final List<RegisterUserValidator> registerUserValidation;

    private final ScoreCalculationService calculateScore;



    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).
                map(it -> mapper.map(it, UserResponse.class));
    }

    public User findById(final String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponse findByIdDTO(final String id) {
        final User user = findById(id);
        return mapper.map(user, UserResponse.class);
    }

    @Transactional
    public UserResponse register(final UserRequest data) {
        final User user = mapper.map(data, User.class);

        this.registerUserValidation.forEach
                (v -> v.validate(new RegisterUserArgs(
                        data,
                        userRepository
                )));

        this.calculateScore.calculateScore(user);

        final Address address = viaCepService.saveAddressFromCep(data.getCep(), data.getAddressNumber());
        user.setAddress(address);

        user.userRegisteredEvent();

        return mapper.map(this.userRepository.save(user), UserResponse.class);
    }
}