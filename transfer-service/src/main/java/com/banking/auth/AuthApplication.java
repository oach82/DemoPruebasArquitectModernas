package com.banking.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @PostMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        if ("admin".equals(user) && "1234".equals(pass)) {
            return "Login OK";
        }
        return "Login Failed";
    }
}