package com.flycamel.accountbookserver.domain.repository;

import com.flycamel.accountbookserver.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
