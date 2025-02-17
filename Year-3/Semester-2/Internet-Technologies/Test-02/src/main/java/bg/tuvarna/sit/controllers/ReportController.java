package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.ReportCreateDto;
import bg.tuvarna.sit.dto.ReportEditDto;
import bg.tuvarna.sit.dto.ReportReadDto;
import bg.tuvarna.sit.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;

  @Autowired
  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @PatchMapping("/edit")
  public ResponseEntity<ReportReadDto> edit(@RequestBody ReportEditDto dto) {
    return new ResponseEntity<>(reportService.edit(dto), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<ReportReadDto> create(@RequestBody ReportCreateDto dto) {
    return new ResponseEntity<>(reportService.create(dto), HttpStatus.CREATED);
  }

  @GetMapping("/slug/{slug}")
  public ResponseEntity<Set<ReportReadDto>> getByNumber(@PathVariable String slug) {
    return new ResponseEntity<>(reportService.getAllBySlug(slug), HttpStatus.OK);
  }
}
