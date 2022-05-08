package com.example.youpin.Controller;

import com.example.youpin.Service.GoodsEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class GoodsEditController {
    @Autowired
    private GoodsEditService goodsEditService;

    @PostMapping("/goodsEdit/addGoods")
    public Map<String, Object> addGoods(@RequestParam String name, @RequestParam Integer sid, Integer tid1, Integer tid2, Integer tid3, Float price) {
        return goodsEditService.addGoods(name, sid, tid1, tid2, tid3, price);
    }

    @PostMapping("/goodsEdit/editGoods")
    public Map<String, Object> editGoods(@RequestParam Integer gid, String name, Integer sid, Float price, Integer tid1,
                                         Integer tid2, Integer tid3) {
        return goodsEditService.editGoods(gid, name, sid, price, tid1, tid2, tid3);
    }

    @PostMapping("/goodsEdit/editGoodsCount")
    public Map<String, Object> editGoodsCount(@RequestParam Integer gid, @RequestParam Integer count) {
        System.out.println("editGoods");
        return goodsEditService.editGoodsCount(gid, count);
    }

    @PostMapping("/goodsEdit/addGoodsBannerPic")
    public Map<String, Object> addGoodsBannerPic(@RequestParam Integer gid, @RequestParam MultipartFile file) {
        return goodsEditService.addBannerPic(gid, file);
    }

    @PostMapping("/goodsEdit/addGoodsDetailPic")
    public Map<String, Object> addGoodsDetailPic(@RequestParam Integer gid, @RequestParam MultipartFile file) {
        return goodsEditService.addDetailPic(gid, file);
    }

    @PostMapping("/goodsEdit/changeGoodsSellingState")
    public Map<String, Object> changeGoodsSellingState(@RequestParam Integer gid, @RequestParam Integer state) {
        return goodsEditService.changeGoodsSellingState(gid, state);
    }

    @PostMapping("/goodsEdit/addGoodsType")
    public Map<String, Object> addGoodsType(@RequestParam Integer gid, String tname1, String tname2, String tname3) {
        return goodsEditService.addGoodsType(gid, tname1, tname2, tname3);
    }

    @PostMapping("/goodsEdit/delGoodsBannerPic")
    public Map<String, Object> delGoodsBannerPic(@RequestParam Integer gid, @RequestParam List<Integer> pid) {
        return goodsEditService.deleteBannerPic(gid, pid);
    }

    @PostMapping("/goodsEdit/delGoodsDetailPic")
    public Map<String, Object> delGoodsDetailPic(@RequestParam Integer gid, @RequestParam List<Integer> pid) {
        return goodsEditService.deleteDetailPic(gid, pid);
    }

    @PostMapping("/goodsEdit/moveBannerPicLeft")
    public Map<String, Object> moveBannerPicLeft(@RequestParam Integer gid, @RequestParam Integer pid) {
        return goodsEditService.moveBannerPicLeft(gid, pid);
    }

    @PostMapping("/goodsEdit/moveBannerPicRight")
    public Map<String, Object> moveBannerPicRight(@RequestParam Integer gid, @RequestParam Integer pid) {
        return goodsEditService.moveBannerPicRight(gid, pid);
    }

    @PostMapping("/goodsEdit/moveDetailPicUp")
    public Map<String, Object> moveDetailPicUp(@RequestParam Integer gid, @RequestParam Integer pid) {
        return goodsEditService.moveDetailPicUp(gid, pid);
    }

    @PostMapping("/goodsEdit/moveDetailPicDown")
    public Map<String, Object> moveDetailPicDown(@RequestParam Integer gid, @RequestParam Integer pid) {
        return goodsEditService.moveDetailPicDown(gid, pid);
    }
}
