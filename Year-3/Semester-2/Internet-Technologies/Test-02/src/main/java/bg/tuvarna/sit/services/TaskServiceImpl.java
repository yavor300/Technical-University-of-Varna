package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.entities.Report;
import bg.tuvarna.sit.entities.Tag;
import bg.tuvarna.sit.entities.Task;
import bg.tuvarna.sit.repositories.TagRepository;
import bg.tuvarna.sit.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository repository;
  private final ModelMapper modelMapper;
  private final TagRepository tagRepository;

  @Autowired
  public TaskServiceImpl(TaskRepository repository, ModelMapper modelMapper, TagRepository tagRepository) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.tagRepository = tagRepository;
  }

  @Override
  public TaskDto create(TaskCreateDto dto) {

    Task task = modelMapper.map(dto, Task.class);
    task = repository.save(task);
    return modelMapper.map(task, TaskDto.class);
  }

  @Override
  public TaskDto getByNumber(Long number) {

    Task byId = repository.findById(number).get();
    return modelMapper.map(byId, TaskDto.class);
  }

  @Override
  public Set<TaskDto> getBySlug(String slug) {

    Set<Report> reports = new HashSet<>();
    for (Tag tag : tagRepository.findAllBySlug(slug)) {
      reports.addAll(tag.getReports());
    }
    Set<Task> tasks = new HashSet<>();
    for (Report report : reports) {
      tasks.add(report.getTask());
    }

    return tasks.stream()
            .map(task -> modelMapper.map(task, TaskDto.class))
            .collect(Collectors.toSet());
  }
}
