package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.TaskCreateDto;
import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  @PostMapping("/create")
  public ResponseEntity<TaskDto> create(@RequestBody TaskCreateDto taskDto) {

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
