package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.UserCreateDto;
import bg.tuvarna.sit.dto.UserLoginDto;
import bg.tuvarna.sit.dto.UserReadDto;
import bg.tuvarna.sit.entities.Role;
import bg.tuvarna.sit.entities.RoleType;
import bg.tuvarna.sit.entities.User;
import bg.tuvarna.sit.repositories.UserRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  @Autowired
  public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public UserReadDto signup(UserCreateDto dto) {

//    modelMapper.typeMap(UserCreateDto.class, User.class)
//            .addMappings(mapper -> {
//              mapper.map(r -> r.getRoles().stream()
//                      .map(t -> {
//                        Role role = new Role();
//                        role.setRole(t);
//                        return role;
//                      }).collect(Collectors.toSet()),
//                      User::setRoles);
//            });

    Converter<Set<RoleType>, Set<Role>> converter = new AbstractConverter<Set<RoleType>, Set<Role>>() {
      @Override
      protected Set<Role> convert(Set<RoleType> roleTypes) {
        return roleTypes.stream().map(
                type -> {
                  Role role = new Role();
                  role.setId(type.getValue());
                  role.setRole(type);
                  return role;
                }
        ).collect(Collectors.toSet());
      }
    };

    modelMapper.typeMap(UserCreateDto.class, User.class)
            .addMappings(mapper -> mapper.using(converter)
                    .map(UserCreateDto::getRoles, User::setRoles));

    User user = modelMapper.map(dto, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user = userRepository.save(user);
    return modelMapper.map(user, UserReadDto.class);
  }

  public UserReadDto login(UserLoginDto loginDto) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            ));

    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authentication);

    UserReadDto userReadDto = new UserReadDto();
    userReadDto.setUsername(authentication.getName());
    return userReadDto;
  }


}
