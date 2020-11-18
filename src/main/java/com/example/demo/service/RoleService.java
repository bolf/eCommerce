package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role role){
        role.setUpdated(new Date());
        return roleRepository.save(role);
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
