package com.example.youpin.Controller;

import com.example.youpin.POJO.User;
import com.example.youpin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserService userService;
    @PostMapping("/userInfo")
    public User getInfo(String uid)
    {
        return userService.getInfo(uid);
    }

}
