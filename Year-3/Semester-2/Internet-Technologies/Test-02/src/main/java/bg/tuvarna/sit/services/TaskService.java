package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;

public interface TaskService {

  TaskDto create(TaskCreateDto dto);

  TaskDto getByNumber(Long number);
}
