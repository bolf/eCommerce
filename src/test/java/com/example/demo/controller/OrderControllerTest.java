package com.example.demo.controller;

import com.example.demo.model.UserOrder;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import com.example.demo.TestUtils;
import com.example.demo.model.Cart;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.junit.BeforeClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

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
        var stubUser = TestUtils.getStubUser();
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        when(userRepository.findByUsername(stubUser.getUsername())).thenReturn(stubUser);

        var userOrderResponseEntity = orderController.submit(stubUser.getUsername());
        assertEquals(stubUser.getUsername(), userOrderResponseEntity.getBody().getUser().getUsername());
    }

    @Test
    public void getOrdersForUserTest(){
        var stubUser = TestUtils.getStubUser();
        stubUser.setCart(new Cart(new ArrayList<>(), BigDecimal.valueOf(100), stubUser));
        when(userRepository.findByUsername(stubUser.getUsername())).thenReturn(stubUser);
        when(orderRepository.findByUser(stubUser)).thenReturn(Arrays.asList(new UserOrder(stubUser)));

        var ordersForUser = orderController.getOrdersForUser(stubUser.getUsername());

        assertEquals(stubUser.getUsername(),ordersForUser.getBody().get(0).getUser().getUsername());
    }

}
