package pl.teardrop.authentication.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findUserByEmail(Email email);

}
