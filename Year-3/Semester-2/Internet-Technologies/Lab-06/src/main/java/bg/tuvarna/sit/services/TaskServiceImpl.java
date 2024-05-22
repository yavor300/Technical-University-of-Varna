package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.entities.Task;
import bg.tuvarna.sit.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository repository;
  private final ModelMapper modelMapper;

  @Autowired
  public TaskServiceImpl(TaskRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  @Override
  public TaskDto create(TaskCreateDto dto) {

    Task task = modelMapper.map(dto, Task.class);
    task = repository.save(task);

    return modelMapper.map(task, TaskDto.class);
  }

  @Override
  public TaskDto getByNumber(Long number) {

    Optional<Task> taskOptional = repository.findById(number);
    if (taskOptional.isEmpty()) {
      throw new EntityNotFoundException("Task not found with ID: " + number);
    }

    return modelMapper.map(taskOptional.get(), TaskDto.class);
  }
}
