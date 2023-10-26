package AuthenticationModule.Authentication.services;

import AuthenticationModule.Authentication.models.*;
import AuthenticationModule.Authentication.repositories.UserRepository;
import AuthenticationModule.Authentication.utils.EmailValidatorUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse createUser(RegisterRequest request) {
        if(validateEmailPasswordCriteria(request)) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        }
        throw new ServiceException("Could not register user.");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticateUser(request.getEmail(), request.getPassword());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ServiceException("User not found"));

        try {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (Exception e) {
            throw new ServiceException("Token generation failed", e);
        }
    }

    private void authenticateUser(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new ServiceException("Invalid credentials", e);
        }
    }

    private boolean validateEmailPasswordCriteria(RegisterRequest request) {
        if (isEmailAlreadyRegistered(request.getEmail())) {
            throw new ServiceException("A user with this email already exists");
        }

        // Validating email
        if (!EmailValidatorUtils.isValid(request.getEmail())) {
            throw new ServiceException("Invalid email address");
        }

        // Validate password length
        if (request.getPassword().length() < 8) {
            throw new ServiceException("Password must be at least 8 characters long");
        }
        return true;
    }


    private boolean isEmailAlreadyRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


}
