package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.ReportCreateDto;
import bg.tuvarna.sit.dto.ReportEditDto;
import bg.tuvarna.sit.dto.ReportReadDto;

import java.util.Set;

public interface ReportService {

  ReportReadDto create(ReportCreateDto dto);
  ReportReadDto edit(ReportEditDto dto);

  Set<ReportReadDto> getAllBySlug(String slug);
}
