package com.example.youpin.Service;

import com.example.youpin.Mapper.UserMapper;
import com.example.youpin.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@EnableAutoConfiguration
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User getInfo(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
