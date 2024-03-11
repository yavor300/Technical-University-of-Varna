package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.TaskNotAddedException;
import bg.tuvarna.sit.exceptions.TaskNotFoundException;
import bg.tuvarna.sit.exceptions.TaskValidationException;
import bg.tuvarna.sit.models.dto.TaskRequestAddBulkDto;
import bg.tuvarna.sit.models.dto.TaskRequestAddDto;
import bg.tuvarna.sit.models.dto.TaskRequestPutDto;
import bg.tuvarna.sit.models.dto.TaskResponseBulkDto;
import bg.tuvarna.sit.models.dto.TaskResponseDto;

public interface TaskService {

  TaskResponseDto add(TaskRequestAddDto dto) throws TaskNotAddedException, TaskValidationException;

  TaskResponseBulkDto addBulk(TaskRequestAddBulkDto dto) throws TaskNotAddedException, TaskValidationException;

  TaskResponseDto getById(String id) throws TaskValidationException, TaskNotFoundException;

  TaskResponseBulkDto getAll();

  TaskResponseDto put(TaskRequestPutDto dto) throws TaskNotFoundException, TaskValidationException;

  TaskResponseDto delete(String id) throws TaskNotFoundException, TaskValidationException;
}
