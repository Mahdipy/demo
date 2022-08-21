package com.neshan.demo.Repositories;

import com.neshan.demo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.jws.soap.SOAPBinding;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
