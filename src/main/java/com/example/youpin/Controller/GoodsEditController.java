package com.example.youpin.Controller;

import com.example.youpin.Service.GoodsEditService;
import com.example.youpin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoodsEditController {
    @Autowired
    private GoodsEditService goodsEditService;
    @PostMapping("/goods/addGoods")
    public Map<String,Object> addGoods(@RequestParam String name, @RequestParam Integer sid, Integer tid1, Integer tid2, Integer tid3) {
        return goodsEditService.addGoods(name,sid,tid1,tid2,tid3);
    }
}
