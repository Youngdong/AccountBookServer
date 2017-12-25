package com.flycamel.accountbookserver.api.controller;

import com.flycamel.accountbookserver.api.dto.UserInfo;
import com.flycamel.accountbookserver.domain.model.User;
import com.flycamel.accountbookserver.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String welcomeUser() {
        log.debug("welcomeUser start...");
        return "welcome";
    }

    @PostMapping(value = "/getUser")
    @ResponseBody
    public UserInfo getUser(@RequestParam Long id) {
        User user = userService.getUser(id);

        return getUserInfoFromUser(user);
    }

    @PostMapping(value = "/getUserByName")
    @ResponseBody
    public UserInfo getUserByName(@RequestParam String name) {
        User user = userService.getUserByName(name);

        return getUserInfoFromUser(user);
    }

    @GetMapping(value = "/getAllUser")
    @ResponseBody
    public List<UserInfo> getAllUser() {
        List<User> userList = userService.getAllUser();

        return userList.stream()
                .map(this::getUserInfoFromUser)
                .collect(Collectors.toList());
    }

    private UserInfo getUserInfoFromUser(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .dateLastLogin(user.getDateLastLogin())
                .build();
    }
}
