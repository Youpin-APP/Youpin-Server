package com.example.youpin.Service;

import com.example.youpin.Mapper.UserMapper;
import com.example.youpin.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@EnableAutoConfiguration
public class RegisterService {
    @Autowired
    private UserMapper userMapper;
    public Map<String, Object> register (String uid, String upw) {
        User user = new User();
        user.setUid(uid);
        user.setUpw(upw);
        user.setUname(uid.toString());
        Map<String, Object> map = new HashMap<>();
        if(!uid.matches("^[0-9]*$") || upw.isEmpty()){
            map.put("status",false);
            return map;
        }
        if(userMapper.selectByPrimaryKey(user) != null){
            map.put("status",false);
            return map;
        }
        userMapper.insert(user);
        map.put("status",true);
        return map;
    }
}
