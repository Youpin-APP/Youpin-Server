package com.example.youpin.Controller;

import com.example.youpin.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
成功：
status = true
失败：
status = false
 */
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/user/register")
    public Map<String,Object> register(String uid, String pw){
        return registerService.register(uid,pw);
    }
}
