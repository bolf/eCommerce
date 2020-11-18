package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.*;
import static org.mockito.Mockito.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartControllerTest {
    private static CartController cartController;
    private static UserRepository userRepository = mock(UserRepository.class);
    private static CartRepository cartRepository = mock(CartRepository.class);
    private static ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeClass
    public static void setUp(){
        cartController = new CartController();
        TestUtils.injectObject(cartController,"userRepository",userRepository);
        TestUtils.injectObject(cartController,"cartRepository",cartRepository);
        TestUtils.injectObject(cartController,"itemRepository",itemRepository);
    }

    @Test
    public void addToCartTest(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";
        var stubUser = new User(userName,null,null,null,goodPassword,null,null);
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        var item = new Item("testItem",BigDecimal.valueOf(10));
        var request = new ModifyCartRequest(userName,1L,1);

        when(userRepository.findByUsername(userName)).thenReturn(stubUser);
        when(itemRepository.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(request);
        var cart = cartResponseEntity.getBody();

        assertEquals(stubUser,cart.getUser());
    }

    @Test
    public void removeFromCart(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";
        var stubUser = new User(userName,null,null,null,goodPassword,null,null);
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        var item = new Item("testItem",BigDecimal.valueOf(10));
        var request = new ModifyCartRequest(userName,1L,1);

        when(userRepository.findByUsername(userName)).thenReturn(stubUser);
        when(itemRepository.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(request);
        var cart = cartResponseEntity.getBody();

        assertEquals(stubUser,cart.getUser());
    }

}
