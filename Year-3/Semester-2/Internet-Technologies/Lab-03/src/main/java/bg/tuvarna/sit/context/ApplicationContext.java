package bg.tuvarna.sit.context;

import bg.tuvarna.sit.service.TaskService;
import bg.tuvarna.sit.service.TaskServiceImpl;

public class ApplicationContext {

  private static final TaskService TASK_SERVICE = TaskServiceImpl.getInstance();

  public static TaskService getTaskService() {
    return TASK_SERVICE;
  }
}
