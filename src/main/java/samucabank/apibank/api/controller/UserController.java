package samucabank.apibank.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.api.dtos.response.UserResponse;
import samucabank.apibank.domain.service.serviceAction.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Find user by ID", description = "Find a user by its ID", method = "GET")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") final String id) {
        final UserResponse user = userService.findByIdDTO(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Creating a new user", method = "POST")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid final UserRequest data) {
        this.userService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}