package com.flycamel.accountbookserver.domain.repository;

import com.flycamel.accountbookserver.domain.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable("userFindByName")
    User findByName(String name);
}
