package com.example.demo;

import com.example.demo.model.User;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObject(Object target,String fieldName,Object toInject){
        try {
            Field declaredField = target.getClass().getDeclaredField(fieldName);
            if (declaredField.trySetAccessible()) {
                declaredField.set(target,toInject);
            } else {
                // package is not opened to the caller to access private member
                //there is no way to inject
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static User getStubUser(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";
        return new User(userName,null,null,null,goodPassword,null,null);
    }

}
