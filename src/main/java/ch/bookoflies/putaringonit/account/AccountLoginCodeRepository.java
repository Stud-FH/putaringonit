package ch.bookoflies.putaringonit.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountLoginCodeRepository extends JpaRepository<AccountLoginCode, String> {
    Optional<AccountLoginCode> findByCode(String code);
}
