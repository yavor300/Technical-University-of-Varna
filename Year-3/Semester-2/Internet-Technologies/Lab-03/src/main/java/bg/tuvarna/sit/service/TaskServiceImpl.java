package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.TaskNotAddedException;
import bg.tuvarna.sit.exceptions.TaskNotFoundException;
import bg.tuvarna.sit.exceptions.TaskValidationException;
import bg.tuvarna.sit.models.dto.TaskRequestAddBulkDto;
import bg.tuvarna.sit.models.dto.TaskRequestAddDto;
import bg.tuvarna.sit.models.dto.TaskRequestPutDto;
import bg.tuvarna.sit.models.dto.TaskResponseBulkDto;
import bg.tuvarna.sit.models.dto.TaskResponseDto;
import bg.tuvarna.sit.models.entities.Task;
import bg.tuvarna.sit.repository.TaskRepository;
import bg.tuvarna.sit.repository.TaskRepositoryImpl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
  private final TaskRepository repository;

  private TaskServiceImpl(TaskRepositoryImpl instance) {
    this.repository = instance;
  }

  private static class SingletonHelper {
    private static final TaskServiceImpl INSTANCE = new TaskServiceImpl(TaskRepositoryImpl.getInstance());
  }

  public static TaskServiceImpl getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  public TaskResponseDto add(TaskRequestAddDto dto) throws TaskNotAddedException, TaskValidationException {

    validateTaskDto(dto);

    Task task = convertToEntity(dto);
    addTaskToRepository(task);

    return convertToReadDto(task);
  }

  @Override
  public TaskResponseBulkDto addBulk(TaskRequestAddBulkDto dto) throws TaskNotAddedException, TaskValidationException {

    validateAllTaskDtos(dto.getTasks());

    List<Task> tasks = convertAllToEntity(dto.getTasks());
    addAllTasksToRepository(tasks);

    return new TaskResponseBulkDto(convertAllToResponseDto(tasks));
  }

  @Override
  public TaskResponseDto getById(String id) throws TaskValidationException, TaskNotFoundException {

    long taskId = parseAndValidateId(id);
    Task task = fetchTaskById(taskId);

    return convertToReadDto(task);
  }

  @Override
  public TaskResponseBulkDto getAll() {

    Collection<Task> tasks = repository.getAll();

    return new TaskResponseBulkDto(convertAllToResponseDto(tasks));
  }

  @Override
  public TaskResponseDto put(TaskRequestPutDto dto)
          throws TaskNotFoundException, TaskValidationException {

    long id = parseAndValidateId(String.valueOf(dto.getId()));
    validateTaskDto(dto);

    Task updatedTask = repository.put(id, dto.getHeading(), dto.getDescription(), dto.getDeadline());
    if (updatedTask == null) {
      throw new TaskNotFoundException("Task with ID " + id + " not found.");
    }

    return convertToReadDto(updatedTask);
  }

  @Override
  public TaskResponseDto delete(String id) throws TaskNotFoundException, TaskValidationException {

    long taskId = parseAndValidateId(id);
    Task task = deleteTaskById(taskId);

    return convertToReadDto(task);
  }

  private void validateTaskDto(TaskRequestAddDto dto) throws TaskValidationException {

    if (dto.getHeading() == null || dto.getDescription() == null || dto.getDeadline() == null) {
      throw new TaskValidationException("None of the Task fields (heading, description, deadline) can be null.");
    }

    if (dto.getDeadline().isBefore(LocalDateTime.now())) {
      throw new TaskValidationException("The deadline must be in the future.");
    }
  }

  private void validateTaskDto(TaskRequestPutDto dto) throws TaskValidationException {

    if (dto.getHeading() == null || dto.getDescription() == null || dto.getDeadline() == null) {
      throw new TaskValidationException("None of the Task fields (heading, description, deadline) can be null.");
    }

    if (dto.getDeadline().isBefore(LocalDateTime.now())) {
      throw new TaskValidationException("The deadline must be in the future.");
    }
  }

  private void validateAllTaskDtos(List<TaskRequestAddDto> dtos) throws TaskValidationException {

    for (TaskRequestAddDto dto : dtos) {
      validateTaskDto(dto);
    }
  }

  private Task convertToEntity(TaskRequestAddDto dto) {

    return new Task(dto.getHeading(), dto.getDescription(), dto.getDeadline());
  }

  private List<Task> convertAllToEntity(List<TaskRequestAddDto> dtos) {

    return dtos.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());
  }

  private void addTaskToRepository(Task task) throws TaskNotAddedException {

    boolean addedSuccessfully = repository.add(task);
    if (!addedSuccessfully) {
      throw new TaskNotAddedException("Task could not be added to the repository.");
    }
  }

  private void addAllTasksToRepository(List<Task> tasks) throws TaskNotAddedException {

    boolean addedSuccessfully = repository.addBulk(tasks);
    if (!addedSuccessfully) {
      throw new TaskNotAddedException("Not all tasks could be added to the repository.");
    }
  }

  private TaskResponseDto convertToReadDto(Task task) {

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    String formattedDeadline = task.getDeadline().format(formatter);
    return new TaskResponseDto(
            Long.toString(task.getId()),
            task.getHeading(),
            task.getDescription(),
            formattedDeadline);
  }

  private Collection<TaskResponseDto> convertAllToResponseDto(Collection<Task> tasks) {

    // IT can work without formatter
    // DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    return tasks.stream()
            .map(task -> new TaskResponseDto(
                    Long.toString(task.getId()),
                    task.getHeading(),
                    task.getDescription(),
                    task.getDeadline().toString()))
            .collect(Collectors.toList());
  }

  private long parseAndValidateId(String id) throws TaskValidationException {

    if (id == null) {
      throw new TaskValidationException("The task ID cannot be null.");
    }

    try {
      return Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new TaskValidationException("The task ID is invalid.");
    }
  }

  private Task fetchTaskById(long taskId) throws TaskNotFoundException {

    Task task = repository.getById(taskId);
    if (task == null) {
      throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
    }

    return task;
  }

  private Task deleteTaskById(long taskId) throws TaskNotFoundException {

    Task task = repository.delete(taskId);
    if (task == null) {
      throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
    }

    return task;
  }
}
