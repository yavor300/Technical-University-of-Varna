package bg.tuvarna.sit.context;

import bg.tuvarna.sit.repository.TaskRepository;
import bg.tuvarna.sit.repository.TaskRepositoryImpl;
import bg.tuvarna.sit.service.TaskService;
import bg.tuvarna.sit.service.TaskServiceImpl;
import java.util.ArrayList;

public class ApplicationContext {

  private static final TaskRepository TASK_REPOSITORY = new TaskRepositoryImpl(new ArrayList<>());

  private static final TaskService TASK_SERVICE = new TaskServiceImpl(TASK_REPOSITORY);

  public static TaskService getTaskService() {
    return TASK_SERVICE;
  }
}
