package com.flycamel.accountbookserver.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@Slf4j
public class LoginController {

    @PostMapping("/loginWithPassword")
    public String loginWithPassword(String username, String password) {
        log.info("loginWithPassword...");
        return "dummy";
    }
}
