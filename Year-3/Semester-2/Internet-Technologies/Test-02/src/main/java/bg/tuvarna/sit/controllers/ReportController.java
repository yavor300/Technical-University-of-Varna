package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.ReportCreateDto;
import bg.tuvarna.sit.dto.ReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

  @PostMapping("/create")
  public ResponseEntity<ReportDto> create(@RequestBody ReportCreateDto dto) {

    ReportDto reportDto = new ReportDto();

    return new ResponseEntity<>(reportDto, HttpStatus.CREATED);
  }
}
