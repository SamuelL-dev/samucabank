package samucabank.apibank.domain.service.businessRule.user.register;

import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.domain.repositories.UserRepository;

public record RegisterUserArgs(UserRequest request, UserRepository userRepository){}
