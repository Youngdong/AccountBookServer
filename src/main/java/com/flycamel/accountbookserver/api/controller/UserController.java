package com.flycamel.accountbookserver.api.controller;

import com.flycamel.accountbookserver.api.dto.UserInfo;
import com.flycamel.accountbookserver.domain.model.User;
import com.flycamel.accountbookserver.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String welcomeUser() {
        log.debug("welcomeUser start...");
        return "welcome";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo getUser(@RequestParam Long id) {
        User user = userService.getUser(id);

        return getUserInfoFromUser(user);
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
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
