package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;

import java.util.Set;

public interface TaskService {

  TaskDto create(TaskCreateDto dto);

  TaskDto getByNumber(Long number);

  Set<TaskDto> getBySlug(String slug);
}
