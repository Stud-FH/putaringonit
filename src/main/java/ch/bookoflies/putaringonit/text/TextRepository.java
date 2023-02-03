package ch.bookoflies.putaringonit.text;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {
    void deleteAllByReferenceKey(String referenceKey);
}
