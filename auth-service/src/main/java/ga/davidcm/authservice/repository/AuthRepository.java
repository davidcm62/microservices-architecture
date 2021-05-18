package ga.davidcm.authservice.repository;

import ga.davidcm.authservice.model.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth, String> {
    Optional<Auth> findByUsernameAndPassword(String username, String password);
}
