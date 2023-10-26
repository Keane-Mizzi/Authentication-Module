package AuthenticationModule.Authentication.repositories;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}