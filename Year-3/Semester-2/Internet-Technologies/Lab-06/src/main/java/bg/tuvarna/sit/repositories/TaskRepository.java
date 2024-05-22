package bg.tuvarna.sit.repositories;

import bg.tuvarna.sit.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
