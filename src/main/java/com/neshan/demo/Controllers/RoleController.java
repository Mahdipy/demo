package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Role;
import com.neshan.demo.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/addrole")
    public Role addRole(@RequestBody Role role){
        return roleRepository.save(role);
    }
}
