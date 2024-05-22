package bg.tuvarna.sit.config;

import bg.tuvarna.sit.dto.TaskDto;
import bg.tuvarna.sit.dto.UserCreateDto;
import bg.tuvarna.sit.dto.UserReadDto;
import bg.tuvarna.sit.entities.Task;
import bg.tuvarna.sit.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static bg.tuvarna.sit.utils.MapperConverterUtil.roleTypesToRoles;
import static bg.tuvarna.sit.utils.MapperConverterUtil.rolesToRoleTypes;

@Configuration
public class BeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.typeMap(Task.class, TaskDto.class)
            .addMappings(mapper -> mapper.map(Task::getId, TaskDto::setNumber));

    modelMapper.typeMap(UserCreateDto.class, User.class)
            .addMappings(mapper -> mapper.using(roleTypesToRoles)
                    .map(UserCreateDto::getRoles, User::setRoles));

    modelMapper.typeMap(User.class, UserReadDto.class)
            .addMappings(mapper -> mapper.using(rolesToRoleTypes)
                    .map(User::getRoles, UserReadDto::setRoles));

    return modelMapper;
  }
}
