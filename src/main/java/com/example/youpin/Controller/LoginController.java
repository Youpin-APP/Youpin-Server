package com.example.youpin.Controller;

import com.example.youpin.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
正确：
success = true
token != null
错误：
success = false
token = null
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/user/login")
    public Map<String, Object> login(String uid, String pw) {
        return loginService.login(uid,pw);
    }
}
