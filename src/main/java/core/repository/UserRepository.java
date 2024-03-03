package core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import core.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findOneByEmail(String email);
    Optional<User> findFirstByToken(String token);
}
