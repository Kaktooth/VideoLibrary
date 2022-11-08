package com.projects.videolibrary.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

  @GetMapping("/log-in")
  public String getLogInPage() {
    return "log-in";
  }
}
