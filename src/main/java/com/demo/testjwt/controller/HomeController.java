package com.demo.testjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @GetMapping({"user","/"})
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
}
