package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Role;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user){
        user.setStatus(Status.ACTIVE);
        user.addRole(roleService.findByName("ROLE_USER"));
        //securing the user
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        user.setSalt(Base64.getEncoder().encodeToString(salt));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword().concat(user.getSalt())));

        Cart cart = new Cart();
        cartService.save(cart);
        user.setCart(cart);
        return save(user);
    }

    public User save(User user){
        user.setUpdated(new Date());
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
