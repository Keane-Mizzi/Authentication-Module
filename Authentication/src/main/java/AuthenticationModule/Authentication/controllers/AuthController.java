package AuthenticationModule.Authentication.controllers;

import AuthenticationModule.Authentication.models.AuthenticationRequest;
import AuthenticationModule.Authentication.models.AuthenticationResponse;
import AuthenticationModule.Authentication.models.RegisterRequest;
import AuthenticationModule.Authentication.services.AuthenticationService;
import AuthenticationModule.Authentication.services.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = authenticationService.createUser(request);
            return ResponseEntity.ok(response);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}