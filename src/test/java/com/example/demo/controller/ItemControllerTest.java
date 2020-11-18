package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.BeforeClass;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemControllerTest {
    private static ItemController itemController;
    private static ItemRepository itemRepository = mock(ItemRepository.class);


    @BeforeClass
    public static void setUp(){
        itemController = new ItemController();
        TestUtils.injectObject(itemController,"itemRepository",itemRepository);
    }

    @Test
    public void getItemsTest(){
        var testName = "testItem";
        when(itemRepository.findAll()).thenReturn(Arrays.asList(new Item(testName, BigDecimal.valueOf(10))));
        var listResponseEntity = itemController.getItems();
        assertEquals(testName, listResponseEntity.getBody().get(0).getName());
    }

    @Test
    public void getItemsByName(){
        var testName = "testItem";
        var testItem = new Item(testName, BigDecimal.valueOf(10));
        when(itemRepository.findByName(testName)).thenReturn(Collections.singletonList(testItem));
        var itemResponseEntity = itemController.getItemsByName(testName);
        assertEquals(testName,itemResponseEntity.getBody().get(0).getName());
    }

    @Test
    public void getItemsById(){
        var testName = "testItem";
        var testId = 1L;
        var testItem = new Item(testName, BigDecimal.valueOf(10));
        testItem.setId(testId);
        when(itemRepository.findById(testId)).thenReturn(java.util.Optional.of(testItem));
        var itemResponseEntity = itemController.getItemById(testId);
        assertEquals(java.util.Optional.of(testId),itemResponseEntity.getBody().getId());
    }
}
