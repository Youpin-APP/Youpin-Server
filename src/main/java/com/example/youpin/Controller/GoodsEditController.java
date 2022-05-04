package com.example.youpin.Controller;

import com.example.youpin.Service.GoodsEditService;
import com.example.youpin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class GoodsEditController {
    @Autowired
    private GoodsEditService goodsEditService;
    @PostMapping("/goodsEdit/addGoods")
    public Map<String,Object> addGoods(@RequestParam String name, @RequestParam Integer sid, Integer tid1, Integer tid2, Integer tid3) {
        return goodsEditService.addGoods(name,sid,tid1,tid2,tid3);
    }
    @PostMapping("/goodsEdit/editGoods")
    public Map<String,Object> editGoods(@RequestParam Integer gid, String name, Integer sid, Float price,Integer tid1,
                                        Integer tid2,Integer tid3) {
        return goodsEditService.editGoods(gid,name,sid,price,tid1,tid2,tid3);
    }
    @PostMapping("/goodsEdit/editGoodsCount")
    public Map<String,Object> editGoodsCount(@RequestParam Integer gid, @RequestParam Integer count) {
        System.out.println("editGoods");
        return goodsEditService.editGoodsCount(gid,count);
    }
    @PostMapping("/goodsEdit/addGoodsBannerPic")
    public Map<String, Object> addGoodsBannerPic(@RequestParam Integer gid, @RequestParam MultipartFile file) {
        return goodsEditService.addBannerPic(gid, file);
    }
}
