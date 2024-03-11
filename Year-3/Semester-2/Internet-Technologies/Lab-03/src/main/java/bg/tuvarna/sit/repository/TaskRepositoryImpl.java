package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class TaskRepositoryImpl implements TaskRepository {

  private final Collection<Task> tasks;
  private final AtomicLong idGenerator;

  private TaskRepositoryImpl() {

    this.tasks = new ArrayList<>();
    this.idGenerator = new AtomicLong(0);
  }

  private static class SingletonHelper {
    private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();
  }

  public static TaskRepositoryImpl getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  public boolean add(Task task) {

    long newId = idGenerator.incrementAndGet();
    task.setId(newId);
    return tasks.add(task);
  }

  @Override
  public boolean addBulk(Collection<Task> newTasks) {

    Collection<Task> addedTasks = new ArrayList<>();

    for (Task task : newTasks) {
      long newId = idGenerator.incrementAndGet();
      task.setId(newId);

      boolean addedSuccessfully = tasks.add(task);
      if (!addedSuccessfully) {
        for (Task addedTask : addedTasks) {
          tasks.remove(addedTask);
        }
        return false;
      }
      addedTasks.add(task);
    }

    return true;
  }

  @Override
  public Task getById(long id) {

    return tasks.stream()
            .filter(task -> task.getId().equals(id))
            .findFirst()
            .orElse(null);
  }

  @Override
  public Collection<Task> getAll() {

    return tasks.stream()
            .sorted()
            .toList();
  }

  @Override
  public Task put(long id, String heading, String description, LocalDateTime deadline) {

    Task task = getById(id);
    if (task == null) {
      return null;
    }

    task.setHeading(heading);
    task.setDescription(description);
    task.setDeadline(deadline);

    return task;
  }

  @Override
  public Task delete(long id) {

    Task taskToRemove = getById(id);

    if (taskToRemove != null) {
      tasks.remove(taskToRemove);
      return taskToRemove;
    }

    return null;
  }
}
