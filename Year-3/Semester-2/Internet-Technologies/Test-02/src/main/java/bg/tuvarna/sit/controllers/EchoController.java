package bg.tuvarna.sit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

  @GetMapping("/echo")
  public String echo() {

    return "echo";
  }
}
