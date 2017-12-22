package com.flycamel.accountbookserver.domain.service;

import com.flycamel.accountbookserver.domain.model.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    List<User> getAllUser();

    User getUserByName(String name);
}
