package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import com.example.demo.TestUtils;
import com.example.demo.model.Cart;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.junit.BeforeClass;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderControllerTest {
    private static OrderController orderController;
    private static UserRepository userRepository = mock(UserRepository.class);
    private static OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeClass
    public static void setUp(){
        orderController = new OrderController();
        TestUtils.injectObject(orderController,"userRepository",userRepository);
        TestUtils.injectObject(orderController,"orderRepository",orderRepository);
    }

    @Test
    public void submitTest(){
        User stubUser = TestUtils.getStubUser();
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        when(userRepository.findByUsername(stubUser.getUsername())).thenReturn(stubUser);

        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(stubUser.getUsername());
        assertEquals(stubUser.getUsername(), userOrderResponseEntity.getBody().getUser().getUsername());
    }

    @Test
    public void submitBadUsername(){
        User stubUser = TestUtils.getStubUser();
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        when(userRepository.findByUsername(stubUser.getUsername())).thenReturn(null);

        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(stubUser.getUsername());
        assertEquals(404,userOrderResponseEntity.getStatusCodeValue());
        assertNull(userOrderResponseEntity.getBody());
    }

    @Test
    public void getOrdersForUserTest(){
        User stubUser = TestUtils.getStubUser();
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        when(userRepository.findByUsername(stubUser.getUsername())).thenReturn(stubUser);
        when(orderRepository.findByUser(stubUser)).thenReturn(Arrays.asList(new UserOrder(stubUser)));

        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser(stubUser.getUsername());

        assertEquals(stubUser.getUsername(),ordersForUser.getBody().get(0).getUser().getUsername());
    }

}
