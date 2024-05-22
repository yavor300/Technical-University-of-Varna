package bg.tuvarna.sit.controllers;

import bg.tuvarna.sit.dto.UserCreateDto;
import bg.tuvarna.sit.dto.UserLoginDto;
import bg.tuvarna.sit.dto.UserReadDto;
import bg.tuvarna.sit.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserReadDto> signup(@Validated @RequestBody UserCreateDto dto) {

    return new ResponseEntity<>(userService.signup(dto), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<UserReadDto> login(@Validated @RequestBody UserLoginDto dto, HttpServletRequest request) {
    return new ResponseEntity<>(userService.login(request, dto), HttpStatus.OK);
  }
}
