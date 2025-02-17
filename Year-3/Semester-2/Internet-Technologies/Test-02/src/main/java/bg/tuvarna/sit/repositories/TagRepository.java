package bg.tuvarna.sit.repositories;

import bg.tuvarna.sit.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByHeadingAndSlug(String heading, String slug);

  Set<Tag> findAllBySlug(String slug);
}
