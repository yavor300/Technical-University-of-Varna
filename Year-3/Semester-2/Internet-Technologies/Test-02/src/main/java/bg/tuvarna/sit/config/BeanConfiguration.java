package bg.tuvarna.sit.config;

import bg.tuvarna.sit.dto.ReportReadDto;
import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.entities.Report;
import bg.tuvarna.sit.entities.Task;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(Task.class, TaskDto.class)
            .addMappings(mapper -> mapper.map(Task::getId, TaskDto::setNumber));
    modelMapper.typeMap(Report.class, ReportReadDto.class)
            .addMappings(mapper -> mapper.map(Report::getId, ReportReadDto::setNumber));
    modelMapper.typeMap(Task.class, TaskDto.class)
            .addMappings(mapper -> {
              mapper.map(Task::getId, TaskDto::setNumber);
            });
    return modelMapper;
  }

}
