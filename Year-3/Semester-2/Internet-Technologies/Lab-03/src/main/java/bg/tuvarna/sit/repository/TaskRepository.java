package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Task;
import java.time.LocalDateTime;
import java.util.Collection;

public interface TaskRepository {

  boolean add(Task task);

  boolean addBulk(Collection<Task> tasks);

  Task getById(long id);

  Collection<Task> getAll();

  Task put(long id, String heading, String description, LocalDateTime deadline);
}
