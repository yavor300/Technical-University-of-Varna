package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.services.TaskService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public ResponseEntity<List<TaskDto>> getAll() {

    List<TaskDto> taskDtos = new ArrayList<>();

    return new ResponseEntity<>(taskDtos, HttpStatus.OK);
  }

  @GetMapping("/{number}")
  public ResponseEntity<TaskDto> getByNumber(@PathVariable Long number) {

    TaskDto byNumber = taskService.getByNumber(number);
    return new ResponseEntity<>(byNumber, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/create")
  public ResponseEntity<TaskDto> create(@Validated @RequestBody TaskCreateDto taskDto) {

    TaskDto created = taskService.create(taskDto);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PatchMapping("/edit")
  public ResponseEntity<TaskDto> update(@RequestBody TaskDto dto) {

    TaskDto updated = new TaskDto();

    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/{number}")
  public ResponseEntity<TaskDto> delete(@PathVariable Long number) {

    TaskDto updated = new TaskDto();

    return new ResponseEntity<>(updated, HttpStatus.NO_CONTENT);
  }
}