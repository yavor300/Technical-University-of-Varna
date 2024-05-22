package bg.tuvarna.sit.services;

import bg.tuvarna.sit.dto.UserCreateDto;
import bg.tuvarna.sit.dto.UserLoginDto;
import bg.tuvarna.sit.dto.UserReadDto;
import bg.tuvarna.sit.entities.User;
import bg.tuvarna.sit.repositories.UserRepository;
import static bg.tuvarna.sit.utils.MapperConverterUtil.roleTypesToRoles;
import static bg.tuvarna.sit.utils.MapperConverterUtil.rolesToRoleTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.stereotype.Service;

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

    User user = modelMapper.map(dto, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user = userRepository.save(user);

    return modelMapper.map(user, UserReadDto.class);
  }

  public UserReadDto login(HttpServletRequest req, UserLoginDto loginDto) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            ));

    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authentication);

    HttpSession session = req.getSession(true);
    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

    User user = userRepository.findByUsername(authentication.getName()).get();
    return modelMapper.map(user, UserReadDto.class);
  }
}
