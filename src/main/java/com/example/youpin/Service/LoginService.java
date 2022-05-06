package com.example.youpin.Service;

import com.example.youpin.Mapper.UserMapper;
import com.example.youpin.POJO.User;
import com.example.youpin.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@EnableAutoConfiguration
public class LoginService {
    @Autowired
    private UserMapper userMapper;
    public Map<String,Object> login (String uid, String upw) {
        Map<String, Object> map = new HashMap<>();
        if(userMapper.existsWithPrimaryKey(uid)){
            User user = userMapper.selectByPrimaryKey(uid);
            if(user.getUpw().equals(upw)){
                map.put("success",true);
                map.put("token",JwtUtil.createToken(user));
                map.put("name",user.getUname());
                return map;
            }
        }
        map.put("success",false);
        map.put("token",null);
        return map;
    }
}
