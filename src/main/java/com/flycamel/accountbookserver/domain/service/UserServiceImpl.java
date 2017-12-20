package com.flycamel.accountbookserver.domain.service;

import com.flycamel.accountbookserver.domain.model.User;
import com.flycamel.accountbookserver.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Resource
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
