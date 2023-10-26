package AuthenticationModule.Authentication.services;

import AuthenticationModule.Authentication.models.AuthenticationRequest;
import AuthenticationModule.Authentication.models.AuthenticationResponse;
import AuthenticationModule.Authentication.models.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse createUser(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
