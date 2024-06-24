package samucabank.apibank.domain.service.serviceAction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.AuthenticationRequest;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.api.dtos.response.RecoveryJwtTokenResponse;
import samucabank.apibank.api.dtos.response.UserResponse;
import samucabank.apibank.domain.enums.user.UserRole;
import samucabank.apibank.domain.model.Address;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.model.UserDetailsImpl;
import samucabank.apibank.domain.repositories.UserRepository;
import samucabank.apibank.domain.service.businessRule.user.register.RegisterUserArgs;
import samucabank.apibank.domain.service.businessRule.user.register.RegisterUserValidator;
import samucabank.apibank.domain.service.customException.user.UserNotFoundException;
import samucabank.apibank.infrastructure.viacep.ViaCepService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final ViaCepService viaCepService;

    private final List<RegisterUserValidator> registerUserValidation;

    private final ScoreCalculationService calculateScore;

    private final TokenService tokenService;

    public Page<UserResponse> findAll(final Pageable pageable) {
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

    public RecoveryJwtTokenResponse authenticateUser(final AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenResponse(tokenService.generateToken(userDetails));

    }

    @Transactional
    public UserResponse save(final UserRequest data) {
        registerUserValidation.forEach
                (v -> v.validate(new RegisterUserArgs(
                        data,
                        userRepository
                )));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        final Address address = viaCepService.saveAddressFromCep(data.getCep(), data.getAddressNumber());

        final User user = this.create(data, encryptedPassword, address);
        user.addRole(UserRole.USER);

        calculateScore.calculateScore(user);

        user.userRegisteredEvent();

        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    private User create(final UserRequest data, String encryptedPassword, final Address address) {
        return User.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .password(encryptedPassword)
                .address(address)
                .document(data.getDocument())
                .phoneNumber(data.getPhoneNumber())
                .dateOfBirth(data.getDateOfBirth())
                .maritalStatus(data.getMaritalStatus())
                .gender(data.getGender())
                .build();
    }
}