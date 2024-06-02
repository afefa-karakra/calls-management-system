package com.example.convoconvert.Controller;

import com.example.convoconvert.Entity.User;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import com.example.convoconvert.Service.Interface.UserServiceInterface;
import com.example.convoconvert.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @CrossOrigin
    @PostMapping("/login")
    String correctUser(@RequestBody User user){
        return userServiceInterface.correctUser(user);
    }

}
