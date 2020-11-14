package com.example.demo.controller;

import org.junit.*;
import static org.mockito.Mockito.*;
import com.example.demo.TestUtils;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserControllerTest {
    private static UserController userController;
    private static UserService userService;
    private static CartService cartService = mock(CartService.class);
    private static UserRepository userRepository = mock(UserRepository.class);
    private static RoleService roleService = mock(RoleService.class);
    private static BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @BeforeClass
    public static void setUp(){
        userController = new UserController();
        userService = new UserService();
        TestUtils.injectObject(userService,"userRepository",userRepository);
        TestUtils.injectObject(userService,"cartService",cartService);
        TestUtils.injectObject(userService,"roleService",roleService);
        TestUtils.injectObject(userService,"bCryptPasswordEncoder",bCryptPasswordEncoder);

        TestUtils.injectObject(userController,"userService",userService);
    }

    @Test
    public void createUserHappyPath(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";

        when(bCryptPasswordEncoder.encode(goodPassword)).thenReturn(goodPassword);
        when(roleService.findByName("USER")).thenReturn(new Role("USER"));

        CreateUserRequest cUr = new CreateUserRequest(userName,goodPassword,goodPassword);
        ResponseEntity<User> createUserResponse = userController.createUser(cUr);
        User createdUser = createUserResponse.getBody();

        assertNotNull(createUserResponse);
        assertEquals(200,createUserResponse.getStatusCodeValue());
        assertNotNull(createdUser);
        assertEquals(userName,createdUser.getUsername());
    }

    @Test
    public void findByUserNameHappyPath(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";
        User stubUser = new User(userName,null,null,null,goodPassword,null,null);

        when(userRepository.findByUsername(userName)).thenReturn(stubUser);

        ResponseEntity<User> userResponseEntity = userController.findByUserName(userName);
        User foundUser = userResponseEntity.getBody();

        assertEquals(userName,foundUser.getUsername());
    }

    @Test
    public void findByIdNameHappyPath(){
        var userName = "testUser";
        var goodPassword = "goodEnoughPass";
        User stubUser = new User(userName,null,null,null,goodPassword,null,null);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(stubUser));

        ResponseEntity<User> userResponseEntity = userController.findById(1L);
        User foundUser = userResponseEntity.getBody();

        assertEquals(userName,foundUser.getUsername());
    }
}
