package com.example.convoconvert.Service;

import com.example.convoconvert.Entity.User;
import com.example.convoconvert.Repository.UserInterfaceRepository;
import com.example.convoconvert.Service.Interface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserInterfaceRepository userInterfaceRepository;



    @Override
    public String correctUser(User user) {
        User u = userInterfaceRepository.findById(user.getEmail()).orElse(null);
        if(u != null) {
            if(u.getPassword().equals(user.getPassword())) {
                return "succesfuly";
            }else {
                return"failed";
            }
        }
        return null;
    }
}
