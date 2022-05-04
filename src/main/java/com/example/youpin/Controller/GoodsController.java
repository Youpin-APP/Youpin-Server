package com.example.youpin.Controller;

import com.example.youpin.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/goods/getInfo")
    public Map<String,Object> getInfo(@RequestParam Integer id){
        return goodsService.getInfo(id);
    }
}
