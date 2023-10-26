package AuthenticationModule.Authentication.config;

import AuthenticationModule.Authentication.models.Role;
import AuthenticationModule.Authentication.models.User;
import AuthenticationModule.Authentication.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {  // Check if the database is empty
                User admin = User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@admin.com") // default login hardcoded
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}