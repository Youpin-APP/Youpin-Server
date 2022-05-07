package com.example.youpin.Controller;

import com.example.youpin.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods/getInfo")
    public Map<String, Object> getInfo(@RequestParam String id) {
        return goodsService.getInfo(id);
    }

    @GetMapping("/goods/getTypeList")
    public Map<String, Object> getTypeLIst(@RequestParam Integer gid) {
        return goodsService.getTypeList(gid);
    }

    @GetMapping("/goods/isAvailableType")
    public Map<String, Object> isAvailableType(Integer tid1, Integer tid2, Integer tid3) {
        return goodsService.isAvailableType(tid1, tid2, tid3);
    }

    @GetMapping("/goods/getGidByType")
    public Map<String, Object> getGidByType(@RequestParam Integer gid, Integer tid1, Integer tid2, Integer tid3) {
        return goodsService.getGidByType(gid, tid1, tid2, tid3);
    }
}
