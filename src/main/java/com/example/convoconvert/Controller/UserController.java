package com.example.convoconvert.Controller;

import com.example.convoconvert.Entity.User;
import com.example.convoconvert.Service.Interface.UserServiceInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
@Tag(name = "User ")
@RestController
@RequestMapping("/User")
public class UserController {

   final private UserServiceInterface userServiceInterface;

    public UserController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @CrossOrigin
    @PostMapping("/login")
    String correctUser(@RequestBody User user){
        return userServiceInterface.correctUser(user);
    }

}
