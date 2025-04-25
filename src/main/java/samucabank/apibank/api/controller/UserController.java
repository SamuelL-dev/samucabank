package samucabank.apibank.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.config.swagger.documentation.UserSwagger;
import samucabank.apibank.api.dtos.request.UserRequest;
import samucabank.apibank.api.dtos.response.UserResponse;
import samucabank.apibank.domain.service.serviceAction.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserSwagger {

    private final UserService service;

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable).getContent());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") final String id) {
        return ResponseEntity.ok(service.findByIdDTO(id));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid final UserRequest data) {
        service.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}