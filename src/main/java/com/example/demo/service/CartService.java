package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public Cart save(Cart cart){
        cart.setUpdated(new Date());
        return cartRepository.save(cart);
    }
}
