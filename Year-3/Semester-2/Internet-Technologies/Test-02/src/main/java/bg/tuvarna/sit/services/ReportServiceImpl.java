package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.*;
import bg.tuvarna.sit.entities.Report;
import bg.tuvarna.sit.entities.Tag;
import bg.tuvarna.sit.entities.Task;
import bg.tuvarna.sit.repositories.ReportRepository;
import bg.tuvarna.sit.repositories.TagRepository;
import bg.tuvarna.sit.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

  private final ReportRepository reportRepository;
  private final TagRepository tagRepository;
  private final TaskRepository taskRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ReportServiceImpl(ReportRepository reportRepository, TagRepository tagRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
    this.reportRepository = reportRepository;
    this.tagRepository = tagRepository;
    this.taskRepository = taskRepository;
    this.modelMapper = modelMapper;
  }


  @Override
  public ReportReadDto create(ReportCreateDto dto) {

    Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
    if (optionalTask.isEmpty()) {
      throw new EntityNotFoundException("Task with not found with ID: " + dto.getTaskId());
    }

    Converter<Set<TagCreateDto>, Set<Tag>> tagDtosToTagEntities = new Converter<>() {
      @Override
      public Set<Tag> convert(MappingContext<Set<TagCreateDto>, Set<Tag>> context) {

        if (context.getSource() == null) {
          return null;
        }

        return context.getSource()
                .stream()
                .map(tagCreateDto -> {
                  Optional<Tag> byHeadingAndSlug = tagRepository.findByHeadingAndSlug(
                          tagCreateDto.getHeading().toLowerCase(Locale.ROOT), tagCreateDto.getSlug().toLowerCase());
                  return byHeadingAndSlug.orElseGet(() -> tagRepository.save(modelMapper.map(tagCreateDto, Tag.class)));

                }).collect(Collectors.toSet());
      }
    };

    modelMapper.typeMap(ReportCreateDto.class, Report.class)
            .addMappings(mapper -> mapper.using(tagDtosToTagEntities)
                    .map(ReportCreateDto::getTags, Report::setTags));

    Report report = modelMapper.map(dto, Report.class);
    report.setTask(optionalTask.get());

    Report saved = reportRepository.save(report);
    return modelMapper.map(saved, ReportReadDto.class);
  }

  @Override
  public ReportReadDto edit(ReportEditDto dto) {

    Optional<Report> optionalReport = reportRepository.findById(dto.getId());
    if (optionalReport.isEmpty()) {
      throw new EntityNotFoundException("Report with not found with ID: " + dto.getId());
    }

    Report report = optionalReport.get();
    report.setContent(dto.getContent() != null ? dto.getContent() : report.getContent());
    report.setHoursWorked(dto.getHoursWorked() != 0 ? dto.getHoursWorked() : report.getHoursWorked());
    report.setDateUpdated(dto.getDateUpdated() != null ? dto.getDateUpdated() : report.getDateUpdated());
    report.setDateCreated(dto.getDateCreated() != null ? dto.getDateCreated() : report.getDateCreated());

    Set<Tag> tags = report.getTags();
    for (TagCreateDto dtoTag : dto.getTags()) {
      Optional<Tag> byHeadingAndSlug = tagRepository.findByHeadingAndSlug(
              dtoTag.getHeading().toLowerCase(Locale.ROOT), dtoTag.getSlug().toLowerCase());
      if (byHeadingAndSlug.isEmpty()) {
        tags.add(tagRepository.save(modelMapper.map(dtoTag, Tag.class)));
      } else {
        tags.add(byHeadingAndSlug.get());
      }
    }
    report.setTags(tags);
    Report saved = reportRepository.save(report);
    return modelMapper.map(saved, ReportReadDto.class);
  }

  @Override
  public Set<ReportReadDto> getAllBySlug(String slug) {

    Set<Report> reports = new HashSet<>();
    for (Tag tag : tagRepository.findAllBySlug(slug)) {
      reports.addAll(tag.getReports());
    }

    return reports.stream()
            .map(report -> modelMapper.map(report, ReportReadDto.class))
            .collect(Collectors.toSet());
  }
}
