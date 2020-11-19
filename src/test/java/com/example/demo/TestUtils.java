package com.example.demo;

import com.example.demo.model.User;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObject(Object target,String fieldName,Object toInject){
        try {
            Field declaredField = target.getClass().getDeclaredField(fieldName);
            boolean wasSet = false;
            if (!declaredField.isAccessible()) {
                wasSet = true;
                declaredField.setAccessible(true);
            }
            declaredField.set(target,toInject);
            if(wasSet){declaredField.setAccessible(false);}

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static User getStubUser(){
        String userName = "testUser";
        String goodPassword = "goodEnoughPass";
        return new User(userName,null,null,null,goodPassword,null,null);
    }

}
