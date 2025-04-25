package samucabank.apibank.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samucabank.apibank.api.dtos.request.AuthenticationRequest;
import samucabank.apibank.api.dtos.response.RecoveryJwtTokenResponse;
import samucabank.apibank.domain.service.serviceAction.UserService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenResponse> login(@RequestBody @Valid final AuthenticationRequest request){
       return ResponseEntity.ok(userService.authenticateUser(request));
    }

}
