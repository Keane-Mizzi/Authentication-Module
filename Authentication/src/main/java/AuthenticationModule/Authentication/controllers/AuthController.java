package AuthenticationModule.Authentication.controllers;

import AuthenticationModule.Authentication.models.AuthenticationRequest;
import AuthenticationModule.Authentication.models.AuthenticationResponse;
import AuthenticationModule.Authentication.models.ErrorResponse;
import AuthenticationModule.Authentication.models.RegisterRequest;
import AuthenticationModule.Authentication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (ServiceException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = authenticationService.createUser(request);
            return ResponseEntity.ok(response);
        } catch (ServiceException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}