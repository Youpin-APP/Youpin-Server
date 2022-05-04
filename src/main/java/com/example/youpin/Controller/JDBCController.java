package com.example.youpin.Controller;

import com.example.youpin.POJO.User;
import com.example.youpin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JDBCController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public List<User> hello()
    {
        List<User> all = userService.findAll();
        return all;
    }

}
