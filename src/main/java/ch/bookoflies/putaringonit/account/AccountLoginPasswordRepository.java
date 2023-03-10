package ch.bookoflies.putaringonit.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountLoginPasswordRepository extends JpaRepository<AccountLoginPassword, String> {
    Optional<AccountLoginPassword> findByUsername(String username);
    boolean existsByUsername(String username);
}
