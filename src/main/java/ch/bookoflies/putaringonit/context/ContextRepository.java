package ch.bookoflies.putaringonit.context;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContextRepository extends JpaRepository<Context, String> {
    Optional<Context> findByName(String name);
}
